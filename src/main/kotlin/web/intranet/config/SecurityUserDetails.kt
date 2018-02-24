package web.intranet.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import web.intranet.auth.domain.Usuario

/**
 * Created by quangio.
 */
class SecurityUserDetails(val usuario: Usuario) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        var list = mutableListOf<SimpleGrantedAuthority>()
        list.add(SimpleGrantedAuthority(usuario.rolNombre))
        return list
    }

    override fun getPassword(): String = usuario.clave

    override fun getUsername(): String = usuario.login

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = usuario.habilitado
}