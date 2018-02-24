package web.intranet.auth.domain.dto

/**
 * Usado para mostrar permisos por rol
 */
data class ModuloDto(
        var id: Long,
        var nombre: String,
        var icono: String,
        var submodulos: MutableList<SubmoduloDto> = mutableListOf()
)