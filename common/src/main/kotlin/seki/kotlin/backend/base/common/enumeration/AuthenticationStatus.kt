package seki.kotlin.backend.base.common.enumeration

enum class AuthenticationStatus {
    REQUIRED_CHANGE_PASSWORD,
    AUTHENTICATED,
    ;

    companion object {
        fun findValue(rawString: String?): AuthenticationStatus? {
            return entries.find { it.name == rawString }
        }
    }
}
