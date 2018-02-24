package web.intranet.auth.domain.dto

class MenuItemDto(
        var nombre: String = "",
        var link: String = "",
        var icono: String = "",
        var subMenuItems: MutableList<MenuItemDto> = mutableListOf()
)