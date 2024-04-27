package seki.kotlin.backend.base.api.service.v1.user

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import seki.kotlin.backend.base.api.component.AccessLogManagementComponent
import seki.kotlin.backend.base.api.db.custom.mapper.LoginClientUserMapper
import seki.kotlin.backend.base.api.enumeration.ApiErrorCode
import seki.kotlin.backend.base.api.enumeration.ApiResponseMsg
import seki.kotlin.backend.base.api.exception.ApiWithMsgException
import seki.kotlin.backend.base.api.extension.ipAddress
import seki.kotlin.backend.base.api.extension.userAgent
import seki.kotlin.backend.base.api.model.session.SessionData
import seki.kotlin.backend.base.api.model.v1.request.PostLoginRequest
import seki.kotlin.backend.base.common.component.VirtualDatetimeComponent
import seki.kotlin.backend.base.common.db.mapper.ClientUserMapper
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordMapper
import seki.kotlin.backend.base.common.db.mapper.update
import seki.kotlin.backend.base.common.db.mapper.updateByPrimaryKeySelective
import seki.kotlin.backend.base.common.db.model.ClientUser
import seki.kotlin.backend.base.common.enumeration.AccessType
import seki.kotlin.backend.base.common.enumeration.AuthenticationStatus
import seki.kotlin.backend.base.common.enumeration.Authority
import seki.kotlin.backend.base.common.enumeration.PasswordStatus
import seki.kotlin.backend.base.common.enumeration.UserStatus
import seki.kotlin.backend.base.common.extension.isAfterEqual
import seki.kotlin.backend.base.common.extension.isBeforeEqual
import seki.kotlin.backend.base.common.log.MsgLogId
import seki.kotlin.backend.base.common.log.MsgLogger
import java.time.LocalDateTime

/**
 * ログインサービス
 */
@Service
class LoginService(
    private val passwordEncoder: PasswordEncoder,
    private val msgLogger: MsgLogger,
    private val virtualDatetimeComponent: VirtualDatetimeComponent,
    private val accessLogManagementComponent: AccessLogManagementComponent,
    private val clientUserMapper: ClientUserMapper,
    private val clientUserPasswordMapper: ClientUserPasswordMapper,
    private val sessionData: SessionData,
    private val loginClientUserMapper: LoginClientUserMapper,
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Throwable::class])
    fun postLogin(
        postLoginRequest: PostLoginRequest,
        request: HttpServletRequest,
    ) {
        val systemDatetime = virtualDatetimeComponent.now()
        val ipAddress = request.ipAddress()
        val userAgent = request.userAgent()
        val requestURI = request.requestURI

        if (accessLogManagementComponent.canAccess(
                ipAddress,
                userAgent,
                systemDatetime,
                AccessType.USER_LOGIN,
                requestURI,
            ).first.not()
        ) {
            msgLogger.msg(MsgLogId.API_LOG_00005, ipAddress)
            throw ApiWithMsgException(ApiErrorCode.ACCESS_LIMITED, ApiResponseMsg.API_MSG_00001.message)
        }

        val loginClientUser =
            loginClientUserMapper.selectLoginClientUser(postLoginRequest.clientUserLoginId!!) ?: run {
                // 取得できなかった場合はアクセス制限のカウント対象
                accessLogManagementComponent.accessLimit(ipAddress, systemDatetime, AccessType.USER_LOGIN, requestURI)
                msgLogger.msg(MsgLogId.API_LOG_00006, postLoginRequest.clientUserLoginId)
                throw ApiWithMsgException(ApiErrorCode.LOGIN_FAILED, ApiResponseMsg.API_MSG_00001.message)
            }

        // 有効期限内の仮パスワードがある場合はそのパスワードで検証する
        val temporaryPasswordMatched =
            if (loginClientUser.passwordStatus == PasswordStatus.TEMPORARY.name &&
                systemDatetime.isBeforeEqual(loginClientUser.temporaryPasswordExpiryDatetime)
            ) {
                passwordEncoder.matches(postLoginRequest.password, loginClientUser.temporaryPassword)
            } else {
                false
            }
        // 仮パスワード発行後に本パスワードを思い出して入力した場合でもログインを成功とさせたいためどちらも検証する
        val passwordMatched = passwordEncoder.matches(postLoginRequest.password, loginClientUser.password)
        if (temporaryPasswordMatched.not() && passwordMatched.not()) {
            // 仮パスワードも本パスワードもどちらも不一致の場合はアクセス制限のカウント対象としてログイン失敗で返却
            accessLogManagementComponent.accessLimit(ipAddress, systemDatetime, AccessType.USER_LOGIN, requestURI)
            msgLogger.msg(MsgLogId.API_LOG_00007, postLoginRequest.clientUserLoginId)
            throw ApiWithMsgException(ApiErrorCode.LOGIN_FAILED, ApiResponseMsg.API_MSG_00001.message)
        }

        // ログイン成功時にアクセスログ管理をクリア
        accessLogManagementComponent.accessLimitRelease(ipAddress, AccessType.USER_LOGIN)

        // 仮パスワード発行後に本パスワードを思い出してログインをした場合、仮パスワード状態をクリアにしたいので有効期限をもとに判定する
        if (passwordMatched && systemDatetime.isAfterEqual(loginClientUser.temporaryPasswordExpiryDatetime)) {
            clientUserPasswordMapper.update {
                set(ClientUserPasswordDynamicSqlSupport.passwordStatus).equalTo(PasswordStatus.CONFIGURED.name)
                set(ClientUserPasswordDynamicSqlSupport.temporaryPassword).equalToNull()
                set(ClientUserPasswordDynamicSqlSupport.temporaryPasswordExpiryDatetime).equalToNull()
                set(ClientUserPasswordDynamicSqlSupport.updateBy).equalTo(loginClientUser.clientUserId.toString())
                set(ClientUserPasswordDynamicSqlSupport.updateAt).equalTo(LocalDateTime.now())
                where { ClientUserPasswordDynamicSqlSupport.clientUserId.isEqualTo(loginClientUser.clientUserId) }
            }
        }
        // 最終ログイン日時
        clientUserMapper.updateByPrimaryKeySelective(
            ClientUser(
                clientUserId = loginClientUser.clientUserId,
                lastLoginDatetime = systemDatetime,
                updateBy = loginClientUser.clientUserId.toString(),
                updateAt = LocalDateTime.now(),
            ),
        )

        // セッション設定
        sessionData.sessionId = request.getSession(true).id
        sessionData.clientUserId = loginClientUser.clientUserId
        sessionData.clientUserLoginId = loginClientUser.clientUserLoginId
        sessionData.userName = loginClientUser.userName
        sessionData.emailAddress = loginClientUser.emailAddress
        sessionData.authenticationStatus =
            if (temporaryPasswordMatched) {
                AuthenticationStatus.REQUIRED_CHANGE_PASSWORD
            } else {
                AuthenticationStatus.AUTHENTICATED
            }
        sessionData.userStatus = UserStatus.ACTIVE
        sessionData.authority = Authority.findValue(loginClientUser.authority)
    }
}
