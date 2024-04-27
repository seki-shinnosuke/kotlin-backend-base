package seki.kotlin.backend.base.common.enumeration

/**
 * ユーザーの権限列挙
 * 定義順が権限の昇順となっているため追加時は注意
 */
enum class Authority {
    USER,
    ADMINISTRATOR,
    ;

    companion object {
        fun findValue(rawString: String?): Authority? {
            return entries.find { it.name == rawString }
        }
    }
}
