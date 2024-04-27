package seki.kotlin.backend.base.api.db.custom.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.type.JdbcType
import seki.kotlin.backend.base.api.db.custom.model.LoginClientUser

@Mapper
interface LoginClientUserMapper {
    @Results(
        id = "selectLoginClientUserResult",
        value = [
            Result(column = "client_user_id", property = "clientUserId", jdbcType = JdbcType.BIGINT, id = true),
            Result(column = "client_user_login_id", property = "clientUserLoginId", jdbcType = JdbcType.VARCHAR),
            Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            Result(column = "email_address", property = "emailAddress", jdbcType = JdbcType.VARCHAR),
            Result(column = "authority", property = "authority", jdbcType = JdbcType.VARCHAR),
            Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            Result(column = "password_status", property = "passwordStatus", jdbcType = JdbcType.VARCHAR),
            Result(column = "temporary_password", property = "temporaryPassword", jdbcType = JdbcType.VARCHAR),
            Result(
                column = "temporary_password_expiry_datetime",
                property = "temporaryPasswordExpiryDatetime",
                jdbcType = JdbcType.TIMESTAMP,
            ),
        ],
    )
    @Select(
        """
            <script>
                SELECT
                    cu.client_user_id,
                    cu.client_user_login_id,
                    cu.user_name,
                    cu.email_address,
                    cu.authority,
                    cup.password,
                    cup.password_status,
                    cup.temporary_password,
                    cup.temporary_password_expiry_datetime
                FROM
                    client_user cu
                    INNER JOIN client_user_password cup ON cu.client_user_id = cup.client_user_id
                WHERE
                    cu.client_user_login_id = #{ clientUserLoginId, jdbcType = VARCHAR }
                    AND user_status = 'ACTIVE'
            </script>
        """,
    )
    fun selectLoginClientUser(clientUserLoginId: String): LoginClientUser?
}
