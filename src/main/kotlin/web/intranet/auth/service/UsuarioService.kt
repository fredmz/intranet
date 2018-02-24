package web.intranet.auth.service

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import web.intranet.auth.domain.Usuario
import web.intranet.auth.domain.UsuarioRepository
import web.intranet.auth.repository.UsuarioQuery
import web.intranet.auth.repository.filter.UsuarioFilter
import web.intranet.utils.service.EmailServiceImpl
import java.util.*

@Service
@Transactional
class UsuarioService(
        val usuarioRepository: UsuarioRepository,
        val usuarioQuery: UsuarioQuery,
        val emailService: EmailServiceImpl
) {
    private val log = LoggerFactory.getLogger(UsuarioService::class.java)

    fun create(usuario: Usuario): String {
        if (usuario.id > 0) {
            throw Exception("No se puede crear un usuario que ya existe")
        }
        if (usuarioRepository.findOneByLoginOrCorreo(usuario.correo, usuario.correo) != null) {
            throw Exception("Ya existe un usuario con ese nombre/correo")
        }
        usuario.clave = encryptClave(usuario.clave)
        usuarioRepository.save(usuario)
        return "Usuario creado correctamente"
    }

    fun update(usuario: Usuario): String {
        if (usuario.id < 1) {
            throw Exception("No se puede actualizar un usuario que no existe")
        }
        usuarioRepository.save(usuario)
        return "Usuario actualizado correctamente"
    }

    @Transactional(readOnly = true)
    fun findByLogin(login: String): Optional<Usuario> {
        return usuarioRepository.findOneByLogin(login)
    }

    fun cambiarClave(claveActual: String, claveNueva: String, claveNueva2: String, usuario: Usuario): String {
        var encoder = BCryptPasswordEncoder()

        if (!encoder.matches(claveActual, usuario.clave)) {
            throw IllegalArgumentException("No ingresó la clave actual")
        }
        if (claveNueva != claveNueva2) {
            throw IllegalArgumentException("Su nueva clave no coincide con la confirmación de la misma")
        }
        if (claveActual == claveNueva) {
            throw IllegalArgumentException("La nueva clave no puede ser igual a la actual")
        }
        return updateClave(claveNueva, usuario)
    }

    @Transactional(readOnly = true)
    fun findAll(usuarioFilter: UsuarioFilter, pageable: Pageable): Page<Usuario> {
        log.info("UsuarioService findAll " + usuarioFilter)
        return usuarioQuery.findAll(usuarioFilter, pageable)
    }

    @Transactional(readOnly = true)
    fun findAll(usuarioFilter: UsuarioFilter): List<Usuario> {
        log.info("UsuarioService findAll " + usuarioFilter)
        return usuarioQuery.findAll(usuarioFilter)
    }

    fun delete(usuario: Usuario): String {
        usuarioRepository.delete(usuario.id)
        return "Usuario ${usuario.login} eliminado correctamente"
    }

    private fun encryptClave(clave: String): String {
        return BCryptPasswordEncoder().encode(clave)
    }

    fun recuperarClave(correo: String, model: Model): String {
        log.info("Usuario Service: recuperarClave: $correo")
        var usuario = usuarioRepository.findOneByCorreo(correo)
        if (usuario == null) {
            throw IllegalArgumentException("No existe ningun usuario con el correo $correo")
        }
        usuario.resetearClave()
        usuarioRepository.save(usuario)
        model.addAttribute("usuario", usuario)
        emailService.send(correo, "Recuperar clave", model, "email/resetear-clave")
        return "Se envió un correo para que proceda a resetear la clave"
    }

    @Transactional(readOnly = true)
    fun findByKeyReset(resetKey: String): Usuario? {
        return usuarioRepository.findOneByResetKey(resetKey)
    }

    fun resetearClave(clave1: String, clave2: String, resetKey: String): String {
        if (clave1 != clave2) {
            throw IllegalArgumentException("Las claves no coinciden")
        }
        var usuario = usuarioRepository.findOneByResetKey(resetKey)
        if (usuario == null) {
            throw IllegalArgumentException("No existe un usuario con la clave de reseteo proporcionada")
        }
        if (!usuario.puedeCambiarLaClavePorReseteo()) {
            throw IllegalArgumentException("La llave ha expirado, debe solicitar un nuevo reseteo de clave")
        }
        usuario.resetKey = null
        usuario.resetDate = null
        return updateClave(clave1, usuario)
    }

    private fun updateClave(clave: String, usuario: Usuario): String {
        usuario.clave = encryptClave(clave)
        usuarioRepository.save(usuario)
        return "Se realizó correctamente el cambio de clave"
    }
}