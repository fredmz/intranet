package web.intranet.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import web.intranet.auth.service.PermisoEnlaceService

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
        val userDetailsService: UserDetailsService,
        val permisoEnlaceService: PermisoEnlaceService
): WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
                .antMatchers("/", "/recuperar-clave", "/resetear-clave", "/js/**", "/css/**", "/img/**", "/webjars/**").permitAll()
                .anyRequest().hasRole("ADMIN")

        /* Permisos de los enlaces por rol y método */
        var matchers = permisoEnlaceService.findAll()
        matchers.forEach { it ->
            if (it.methodIsANY()) {
                http.authorizeRequests().antMatchers(*it.arrayEnlaces()).hasRole(it.rol.replace("ROLE_", ""))
            } else {
                http.authorizeRequests().antMatchers(it.httpMethod(), *it.arrayEnlaces()).hasRole(it.rol.replace("ROLE_", ""))
            }
        }

        http
            .formLogin()
                .loginPage("/")
                .passwordParameter("clave")
                .usernameParameter("nombre")
                .defaultSuccessUrl("/menu")
                .permitAll()
            .and()
                .csrf().ignoringAntMatchers("/api/**")
            .and()
                .logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .rememberMe()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder())
    }
}