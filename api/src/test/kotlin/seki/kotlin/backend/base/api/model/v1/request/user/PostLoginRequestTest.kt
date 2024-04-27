package seki.kotlin.backend.base.api.model.v1.request.user

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.Validator
import seki.kotlin.backend.base.api.enumeration.AnnotationName
import seki.kotlin.backend.base.api.model.v1.request.PostLoginRequest

@SpringBootTest(classes = [ValidationAutoConfiguration::class])
internal class PostLoginRequestTest {
    @Autowired
    var validator: Validator? = null

    companion object {
        private const val REQUEST_CLASS_NAME = "PostLoginRequest"
    }

    @DisplayName("PostLoginRequest：正常")
    @Test
    fun testPostLoginValidationSuccess01() {
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "base",
                password = "password",
            )

        val bindingResult: BindingResult = BindException(requestBody, REQUEST_CLASS_NAME)
        validator!!.validate(requestBody, bindingResult)
        Assertions.assertThat(bindingResult.fieldError).isNull()
    }

    @DisplayName("PostLoginRequest：異常：空文字")
    @Test
    fun testPostLoginValidationFail02() {
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "",
                password = "",
            )

        val bindingResult: BindingResult = BindException(requestBody, REQUEST_CLASS_NAME)
        validator!!.validate(requestBody, bindingResult)
        val clientUserLoginIdFields = bindingResult.fieldErrors.filter { it.field == "clientUserLoginId" }
        Assertions.assertThat(clientUserLoginIdFields.any { it.code == AnnotationName.NOT_EMPTY.value }).isTrue
        val passwordFields = bindingResult.fieldErrors.filter { it.field == "password" }
        Assertions.assertThat(passwordFields.any { it.code == AnnotationName.NOT_EMPTY.value }).isTrue
    }

    @DisplayName("PostLoginRequest：異常：桁数オーバー")
    @Test
    fun testPostLoginValidationFail03() {
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "a".repeat(51),
                password = "a".repeat(33),
            )

        val bindingResult: BindingResult = BindException(requestBody, REQUEST_CLASS_NAME)
        validator!!.validate(requestBody, bindingResult)
        val clientUserLoginIdFields = bindingResult.fieldErrors.filter { it.field == "clientUserLoginId" }
        Assertions.assertThat(clientUserLoginIdFields.any { it.code == AnnotationName.SIZE.value }).isTrue
        val passwordFields = bindingResult.fieldErrors.filter { it.field == "password" }
        Assertions.assertThat(passwordFields.any { it.code == AnnotationName.SIZE.value }).isTrue
    }
}
