package web.intranet.auth.service

import org.springframework.stereotype.Service
import web.intranet.auth.domain.dto.MenuItemDto
import web.intranet.auth.repository.MenuItemQuery

@Service
class MenuItemService(
        val menuItemQuery: MenuItemQuery
) {
    fun findAll(rol: String): List<MenuItemDto> {
        return menuItemQuery.findAll(rol)
    }
}