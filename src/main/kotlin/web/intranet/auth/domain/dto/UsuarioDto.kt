package web.intranet.auth.domain.dto

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank

data class UsuarioDto(
        @field:NotBlank
        var login: String = "",
        @field:NotBlank @field:Email
        var correo: String = "",
        var habilitado: Boolean = true
)