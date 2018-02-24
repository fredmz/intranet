package web.intranet.auth.web.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import web.intranet.auth.domain.Permiso
import web.intranet.auth.domain.PermisoEnlace
import web.intranet.auth.domain.PermisoEnlaceId
import web.intranet.auth.domain.PermisoId
import web.intranet.auth.domain.dto.EnlaceDto
import web.intranet.auth.domain.dto.SubmoduloDto
import web.intranet.auth.service.EnlaceService
import web.intranet.auth.service.PermisoEnlaceService
import web.intranet.auth.service.SubmoduloService

@RestController
class EnlaceResource(
        val enlaceService: EnlaceService
) {
    @GetMapping("/api/auth/submodulos/{submoduloId}/enlaces")
    fun getAllBySubmoduloId(@PathVariable(name = "submoduloId")
                         submoduloId: String,
                         @RequestParam(name = "rol", required = true)
                         rol: String): List<EnlaceDto> {
        return enlaceService.getAllBySubmoduloId(submoduloId, rol)
    }

    @PostMapping("/api/auth/enlace/permiso-agregar/{enlaceId}/{rol}")
    @ResponseBody
    fun agregarPermiso(@PathVariable(name = "rol") rol: String,
                                @PathVariable(name = "enlaceId") enlaceId: Long): ResponseEntity<String> {
        val rpta = enlaceService.agregarPermiso(PermisoEnlace(PermisoEnlaceId(rol, enlaceId)))
        return ResponseEntity.ok().body(rpta)
    }

    @PostMapping("/api/auth/enlace/permiso-quitar/{enlaceId}/{rol}")
    @ResponseBody
    fun quitarPermiso(@PathVariable(name = "rol") rol: String,
                                @PathVariable(name = "enlaceId") enlaceId: Long): ResponseEntity<String> {
        val rpta = enlaceService.quitarPermiso(PermisoEnlace(PermisoEnlaceId(rol, enlaceId)))
        return ResponseEntity.ok().body(rpta)
    }
}