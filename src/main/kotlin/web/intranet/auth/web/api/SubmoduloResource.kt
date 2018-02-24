package web.intranet.auth.web.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import web.intranet.auth.domain.Permiso
import web.intranet.auth.domain.PermisoId
import web.intranet.auth.domain.dto.SubmoduloDto
import web.intranet.auth.service.SubmoduloService

@RestController
class SubmoduloResource(
        val submoduloService: SubmoduloService
) {
    @GetMapping("/api/modulo/{moduloId}/submodulos")
    fun getAllByModuloId(@PathVariable(name = "moduloId")
                         moduloId: Long,
                         @RequestParam(name = "rol", required = true)
                         rol: String): List<SubmoduloDto> {
        return submoduloService.findByModuloId(moduloId, rol)
    }

    @PostMapping("/api/auth/submodulo-agregar/{submoduloId}/{rol}")
    @ResponseBody
    fun agregarPermisoSubModulo(@PathVariable(name = "rol") rol: String,
                                @PathVariable(name = "submoduloId") submoduloId: String): ResponseEntity<String> {
        val rpta = submoduloService.agregarPermiso(Permiso(PermisoId(rol, submoduloId)))
        return ResponseEntity.ok().body(rpta)
    }

    @PostMapping("/api/auth/submodulo-quitar/{submoduloId}/{rol}")
    @ResponseBody
    fun quitarPermisoSubModulo(@PathVariable(name = "rol") rol: String,
                                @PathVariable(name = "submoduloId") submoduloId: String): ResponseEntity<String> {
        val rpta = submoduloService.quitarPermiso(Permiso(PermisoId(rol, submoduloId)))
        return ResponseEntity.ok().body(rpta)
    }
}