package web.intranet.auth.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.commons.lang3.RandomStringUtils
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.data.jpa.repository.JpaRepository
import org.thymeleaf.standard.expression.MinusExpression
import web.intranet.auth.domain.dto.UsuarioDto
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "auth_usuario")
data class Usuario(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        @Column(nullable = false, length = 45) @field:Size(min = 3, max = 30, message = "El nombre del usuario debe estar comprendido entre 3 y 30 caracteres")
        var login: String = "",

        @Column(nullable = false, length = 250) @field:NotBlank @JsonIgnore @field:Size(min = 6, message = "La clave debe tener al menos 6 letras")
        var clave: String = "",

        @Column(nullable = false, length = 250) @field:NotBlank @field:Email @field:Size(max = 250)
        var correo: String = "",

        @Column(nullable = false)
        var habilitado: Boolean = true,

        @Column(length = 150)
        var resetKey: String? = null,

        @Column(columnDefinition = "timestamp")
        var resetDate: Date? = null,

        @Column(length = 45, nullable = false)
        var rolNombre: String = ""
) {
    /*
    * Necesario para evitar el HHH000100: Fail-safe cleanup (collections) : org.hibernate.engine.loading.internal.CollectionLoadContext
    * en caso de relaciones ManyToMany
    * */
    override fun hashCode(): Int = (id * 6).toInt()

    fun fromUsuarioVM(usuarioVM: UsuarioDto) {
        login = usuarioVM.login
        correo = usuarioVM.correo
        habilitado = usuarioVM.habilitado
    }

    fun resetearClave() {
        resetDate = Date()
        resetKey = RandomStringUtils.randomNumeric(20).toString()
    }

    fun puedeCambiarLaClavePorReseteo(): Boolean {
        if (resetDate == null) {
            return false
        }
        var ahora = Date()
        var minutos = TimeUnit.MILLISECONDS.toMinutes(ahora.time - resetDate!!.time)
        println("minutos: ${minutos}")
        return minutos <= 30
    }
}

interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun findOneByLogin(login: String): Optional<Usuario>
    fun findOneByLoginOrCorreo(login: String, correo: String): Usuario?
    fun findOneByResetKey(resetKey: String): Usuario?
    fun findOneByCorreo(correo: String): Usuario?
}

