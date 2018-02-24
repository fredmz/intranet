package web.intranet.auth.web.api

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import web.intranet.auth.domain.dto.MenuItemDto
import web.intranet.auth.service.MenuItemService
import web.intranet.auth.service.UsuarioService

@RestController
class MenuItemResource(
        val itemService: MenuItemService
) {
    @GetMapping("/api/auth/menu-items")
    fun findAll(auth: Authentication): List<MenuItemDto> {
        return itemService.findAll(auth.authorities.first().authority)
    }
}