package web.intranet.config

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import web.intranet.auth.domain.Usuario
import web.intranet.auth.domain.UsuarioRepository

@Service("userDetailsService")
class SecurityUserDetailsService(
        val usuarioRepository: UsuarioRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(login: String): UserDetails {
        var usuarioFromDatabase = usuarioRepository.findOneByLogin(login)
        return usuarioFromDatabase.map { usuario: Usuario -> SecurityUserDetails(usuario) }.orElseThrow({UsernameNotFoundException("No se encontró el usuario")})
    }
}