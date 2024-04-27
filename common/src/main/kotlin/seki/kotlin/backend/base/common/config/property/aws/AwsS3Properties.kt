package seki.kotlin.backend.base.common.config.property.aws

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "aws.s3")
data class AwsS3Properties(
    val endpoint: String,
    val region: String,
    val isIamRoleOn: Boolean,
    val accessKey: String,
    val secretKey: String,
    val bucketName: String,
    val isCdnOn: Boolean,
    val keyPairId: String,
    val privateKey: String,
    val cdnEndpoint: String,
)
