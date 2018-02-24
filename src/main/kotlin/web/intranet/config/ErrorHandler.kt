package web.intranet.config

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


class ErrorHandler {

    private val log = LoggerFactory.getLogger(ErrorController::class.java)

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exception(throwable: Throwable?, model: Model): String {
        log.error("Exception during execution of SpringSecurity application", throwable)
        val errorMessage = if (throwable != null) throwable.message else "Error desconocido"
        model.addAttribute("errorMessage", errorMessage)
        return "error"
    }
}