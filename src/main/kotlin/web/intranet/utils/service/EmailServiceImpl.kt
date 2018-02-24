package web.intranet.utils.service

import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.thymeleaf.context.Context
import org.thymeleaf.spring4.SpringTemplateEngine
import java.nio.charset.StandardCharsets

@Service
class EmailServiceImpl(
        private val emailSender: JavaMailSender,
        private val templateEngine: SpringTemplateEngine,
        private val environment: Environment
) {
    private fun normalSend(from: String, nameFrom: String, to: String, subject: String, model: Model, plantilla: String) {
        var message = emailSender.createMimeMessage()
        var helper = MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name())
        var ctx = Context()
        ctx.setVariables(model.asMap())
        ctx.setVariable("baseUrl", environment.getProperty("myapp.baseurl"))
        ctx.setVariable("appName", environment.getProperty("spring.application.name"))
        var html = templateEngine.process(plantilla, ctx)

        helper.setTo(to)
        helper.setText(html, true)
        helper.setSubject(subject)
        helper.setFrom(from, nameFrom)

        emailSender.send(message)
    }

    fun send(to: String, subject: String, model: Model, plantilla: String) {
        normalSend(environment.getProperty("spring.mail.username"),
                environment.getProperty("spring.application.name"),
                to, subject, model, plantilla)
    }
}