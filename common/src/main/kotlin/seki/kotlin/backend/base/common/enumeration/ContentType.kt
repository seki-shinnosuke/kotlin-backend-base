package seki.kotlin.backend.base.common.enumeration

import org.springframework.http.MediaType

/**
 * コンテンツタイプの列挙型
 * 基本、システムで対応する拡張子を全量定義し、アップロード、ダウンロード可能な拡張子はメソッドで定義して返却する
 */
enum class ContentType(val value: String, val mediaType: String) {
    JPG(value = "jpg", mediaType = MediaType.IMAGE_JPEG_VALUE),
    JPEG(value = "jpeg", mediaType = MediaType.IMAGE_JPEG_VALUE),
    PNG(value = "png", mediaType = MediaType.IMAGE_PNG_VALUE),
    PDF(value = "pdf", mediaType = MediaType.APPLICATION_PDF_VALUE),
    DOC(value = "doc", mediaType = "application/msword"),
    DOCX(value = "docx", mediaType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    XLS(value = "xls", mediaType = "application/vnd.ms-excel"),
    XLSX(value = "xlsx", mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    PPT(value = "ppt", mediaType = "application/vnd.ms-powerpoint"),
    PPTX(value = "pptx", mediaType = "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    TXT(value = "txt", mediaType = MediaType.TEXT_PLAIN_VALUE),
    JSON(value = "json", mediaType = MediaType.APPLICATION_JSON_VALUE),
}
