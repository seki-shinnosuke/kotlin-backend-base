package seki.kotlin.backend.base.common.component.aws

import org.springframework.stereotype.Component
import seki.kotlin.backend.base.common.config.property.aws.AwsS3Properties
import seki.kotlin.backend.base.common.enumeration.ContentType
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.http.apache.ApacheHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import software.amazon.awssdk.services.s3.model.Delete
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.ObjectIdentifier
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Object
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.io.InputStream
import java.net.URI
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Base64

/**
 * AmazonS3に対する以下のファイル操作処理を行う
 * ・ファイルアップロード(Base64で受け取る)
 * ・ファイルアップロードURL発行
 * ・ファイルダウンロード
 * ・公開用URL発行
 * ・ファイル削除
 */
@Component
class AwsS3Component(
    private val awsS3Properties: AwsS3Properties,
) {
    companion object {
        // S3ファイル公開有効期限時間(固定で1時間)
        const val GET_URL_EXPIRATION_HOUR = 1L

        // S3ファイルアップロードURL有効期限時間(固定で1分)
        const val PUT_URL_EXPIRATION_MINUTES = 1L

        // S3同時削除上限
        const val LIMIT_DELETE_SIZE = 1000
    }

    private lateinit var s3Client: S3Client
    private lateinit var presigner: S3Presigner
    private lateinit var cloudFrontUtilities: CloudFrontUtilities
    private var privateKey: PrivateKey? = null

    init {
        val credentialsProvider =
            if (awsS3Properties.isIamRoleOn) {
                ContainerCredentialsProvider.builder().asyncCredentialUpdateEnabled(true).build()
            } else {
                StaticCredentialsProvider.create(AwsBasicCredentials.create(awsS3Properties.accessKey, awsS3Properties.secretKey))
            }
        val region = Region.of(awsS3Properties.region)
        s3Client =
            S3Client.builder()
                .region(region)
                .endpointOverride(URI(awsS3Properties.endpoint))
                .credentialsProvider(credentialsProvider)
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .httpClient(ApacheHttpClient.builder().socketTimeout(Duration.ofSeconds(30)).build())
                .build()
        presigner =
            S3Presigner.builder()
                .region(region)
                .endpointOverride(URI(awsS3Properties.endpoint))
                .credentialsProvider(credentialsProvider)
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .s3Client(s3Client)
                .build()
        cloudFrontUtilities = CloudFrontUtilities.create()
        privateKey = if (awsS3Properties.isCdnOn) getPrivateKey() else null
    }

    /**
     * ファイルアップロード
     */
    fun fileUpload(
        targetFileBytes: ByteArray,
        keyName: String,
        contentType: ContentType,
    ) {
        try {
            val putObjectRequest =
                PutObjectRequest.builder().bucket(
                    awsS3Properties.bucketName,
                ).key(keyName).contentType(contentType.mediaType).contentLength(targetFileBytes.size.toLong()).build()
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(targetFileBytes))
        } catch (e: Throwable) {
            throw e
        }
    }

    /**
     * ファイルアップロードURL発行
     */
    fun fileUploadUrl(
        keyName: String,
        contentType: ContentType,
    ): String {
        try {
            // CDNを利用できない環境の場合はS3デフォルトドメインでURLを発行する
            if (!awsS3Properties.isCdnOn) {
                val putObjectRequest =
                    PutObjectRequest.builder().bucket(
                        awsS3Properties.bucketName,
                    ).key(keyName).contentType(contentType.mediaType).build()
                val putObjectPresignRequest =
                    PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(PUT_URL_EXPIRATION_MINUTES))
                        .putObjectRequest(putObjectRequest).build()
                return presigner.presignPutObject(putObjectPresignRequest).url().toString()
            }
            // CDN利用の場合はCloudFront
            val cannedRequest =
                CannedSignerRequest.builder()
                    .resourceUrl(awsS3Properties.cdnEndpoint + keyName)
                    .privateKey(privateKey)
                    .keyPairId(awsS3Properties.keyPairId)
                    .expirationDate(Instant.now().plus(PUT_URL_EXPIRATION_MINUTES, ChronoUnit.MINUTES))
                    .build()
            return cloudFrontUtilities.getSignedUrlWithCannedPolicy(cannedRequest).url()
        } catch (e: Throwable) {
            throw e
        }
    }

    /**
     * キー名取得(Prefixベース)
     */
    fun keyNameByPrefix(prefix: String): List<String> {
        try {
            val s3objects = mutableListOf<S3Object>()
            val listObjectsV2Request = ListObjectsV2Request.builder().bucket(awsS3Properties.bucketName).prefix(prefix)
            var result = s3Client.listObjectsV2(listObjectsV2Request.build())
            s3objects.plus(result.contents())
            while (result.isTruncated) {
                result = s3Client.listObjectsV2(listObjectsV2Request.continuationToken(result.nextContinuationToken()).build())
                s3objects.plus(result.contents())
            }
            return s3objects.map { it.key() }
        } catch (e: Throwable) {
            throw e
        }
    }

    /**
     * ファイルダウンロード
     */
    fun fileDownload(keyName: String): ByteArray {
        try {
            val getObjectRequest = GetObjectRequest.builder().bucket(awsS3Properties.bucketName).key(keyName).build()
            val s3object = s3Client.getObjectAsBytes(getObjectRequest)
            return s3object.asByteArray()
        } catch (e: Throwable) {
            throw e
        }
    }

    /**
     * ファイルダウンロード
     */
    fun fileDownloadStream(keyName: String): InputStream {
        try {
            val getObjectRequest = GetObjectRequest.builder().bucket(awsS3Properties.bucketName).key(keyName).build()
            val s3object = s3Client.getObjectAsBytes(getObjectRequest)
            return s3object.asInputStream()
        } catch (e: Throwable) {
            throw e
        }
    }

    /**
     * 公開用URL発行
     */
    fun fileShareUrl(keyName: String): String {
        try {
            // CDNを利用できない環境の場合はS3デフォルトドメインでURLを発行する
            if (!awsS3Properties.isCdnOn) {
                val getObjectRequest = GetObjectRequest.builder().bucket(awsS3Properties.bucketName).key(keyName).build()
                val getObjectPresignRequest =
                    GetObjectPresignRequest.builder().signatureDuration(
                        Duration.ofHours(GET_URL_EXPIRATION_HOUR),
                    ).getObjectRequest(getObjectRequest).build()
                return presigner.presignGetObject(getObjectPresignRequest).url().toString()
            }
            // CDN利用の場合はCloudFront
            val cannedRequest =
                CannedSignerRequest.builder()
                    .resourceUrl(awsS3Properties.cdnEndpoint + keyName)
                    .privateKey(privateKey)
                    .keyPairId(awsS3Properties.keyPairId)
                    .expirationDate(Instant.now().plus(GET_URL_EXPIRATION_HOUR, ChronoUnit.HOURS))
                    .build()
            return cloudFrontUtilities.getSignedUrlWithCannedPolicy(cannedRequest).url()
        } catch (e: Throwable) {
            throw e
        }
    }

    /**
     * ファイル削除
     */
    fun filesDelete(keyNames: List<String>) {
        try {
            val objIdsChunked = keyNames.map { ObjectIdentifier.builder().key(it).build() }.chunked(LIMIT_DELETE_SIZE)
            objIdsChunked.forEach { objIds ->
                val deleteObjectRequest =
                    DeleteObjectsRequest.builder().bucket(
                        awsS3Properties.bucketName,
                    ).delete(Delete.builder().objects(objIds).build()).build()
                s3Client.deleteObjects(deleteObjectRequest)
            }
        } catch (e: Throwable) {
            throw e
        }
    }

    /**
     * 秘密鍵を取得
     */
    private fun getPrivateKey(): PrivateKey {
        val bytes = Base64.getDecoder().decode(awsS3Properties.privateKey.toByteArray())
        val keySpec = PKCS8EncodedKeySpec(bytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }
}
