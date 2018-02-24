package web.intranet.auth.domain.dto

/**
 * Es para tener el listado de submodulos que tiene un rol
 */
data class SubmoduloDto(
        var id: String = "",
        var nombre: String = "",
        var icono: String = "",
        var enlace: String = "",
        var rol: String? = null,
        var enlaces: MutableList<EnlaceDto> = mutableListOf()
)