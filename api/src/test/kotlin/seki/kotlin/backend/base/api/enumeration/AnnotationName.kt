package seki.kotlin.backend.base.api.enumeration

/**
 * バリデーションで利用するアノテーション名のEnumクラス.
 * ※リクエストバリデーションのテスト検証で利用
 */
enum class AnnotationName(val value: String) {
    /** NotNull */
    NOT_NULL("NotNull"),

    /** NotEmpty */
    NOT_EMPTY("NotEmpty"),

    /** NotBlank */
    NOT_BLANK("NotBlank"),

    /** Size */
    SIZE("Size"),

    /** Range */
    RANGE("Range"),

    /** AssertTrue */
    ASSERT_TRUE("AssertTrue"),

    /** AssertFalse */
    ASSERT_FALSE("AssertFalse"),
}
