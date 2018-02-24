package web.intranet.auth.service

import org.springframework.stereotype.Service
import web.intranet.auth.domain.Permiso
import web.intranet.auth.domain.PermisoRepository
import web.intranet.auth.domain.dto.SubmoduloDto
import web.intranet.auth.repository.SubmoduloQuery

@Service
class SubmoduloService(
        val submoduloQuery: SubmoduloQuery,
        val permisoRepository: PermisoRepository
) {

    fun findByModuloId(moduloId: Long, rol: String): List<SubmoduloDto> {
        return submoduloQuery.findAllByModuloIdAndRol(moduloId, rol)
    }

    fun agregarPermiso(permiso: Permiso): String {
        if (permiso.id.rol != "ROLE_ADMIN") {
            permisoRepository.save(permiso)
        }
        return "Permiso del submodulo ${permiso.id.submoduloId} agregado a ${permiso.id.rol}"
    }

    fun quitarPermiso(permiso: Permiso): String {
        if (permiso.id.rol != "ROLE_ADMIN") {
            permisoRepository.delete(permiso)
        }
        return "Permiso del submodulo ${permiso.id.submoduloId} quitado a ${permiso.id.rol}"
    }
}