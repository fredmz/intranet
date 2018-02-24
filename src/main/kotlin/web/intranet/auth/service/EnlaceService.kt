package web.intranet.auth.service

import org.springframework.stereotype.Service
import web.intranet.auth.domain.PermisoEnlace
import web.intranet.auth.domain.PermisoEnlaceRepository
import web.intranet.auth.domain.dto.EnlaceDto
import web.intranet.auth.repository.EnlaceQuery

@Service
class EnlaceService(
        private val enlaceQuery: EnlaceQuery,
        private val permisoEnlaceRepository: PermisoEnlaceRepository
) {
    fun getAllBySubmoduloId(submoduloId: String, rol: String): List<EnlaceDto> {
        return enlaceQuery.findAllBySubmoduloIdAndRol(submoduloId, rol)
    }

    fun agregarPermiso(permisoEnlace: PermisoEnlace): String {
        if (permisoEnlace.id.rol != "ROLE_ADMIN") {
            permisoEnlaceRepository.save(permisoEnlace)
        }
        return "Enlace agregado al usuario ${permisoEnlace.id.rol}"
    }

    fun quitarPermiso(permisoEnlace: PermisoEnlace): String {
        if (permisoEnlace.id.rol != "ROLE_ADMIN") {
            permisoEnlaceRepository.delete(permisoEnlace)
        }
        return "Enlace quitado al usuario ${permisoEnlace.id.rol}"
    }
}