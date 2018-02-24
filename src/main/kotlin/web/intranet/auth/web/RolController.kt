package web.intranet.auth.web

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import web.intranet.auth.domain.Rol
import web.intranet.auth.repository.filter.RolFilter
import web.intranet.auth.service.ModuloService
import web.intranet.auth.service.RolService
import web.intranet.auth.web.utils.Pager
import javax.validation.Valid

@Controller
class RolController(
        val rolService: RolService,
        val moduloService: ModuloService
) {
    private val log = LoggerFactory.getLogger(RolController::class.java)

    @GetMapping("/auth/roles")
    fun listar(model: Model, rolFilter: RolFilter, pageable: Pageable): String {
        model.addAttribute("filtro", rolFilter)
        var lista = rolService.findAll(rolFilter, pageable)
        model.addAttribute("titulo", "Gestión de roles")
        model.addAttribute("lista", lista)
        model.addAttribute("pager", Pager(lista.totalPages, lista.number))
        return "auth/rol/listar"
    }

    @GetMapping("/auth/roles-nuevo")
    fun crearForm(model: Model): String {
        model.addAttribute("rol", Rol())
        return "auth/rol/form"
    }

    @PostMapping("/auth/roles-nuevo")
    @ResponseBody
    fun crear(@Valid rol: Rol): ResponseEntity<String> {
        return ResponseEntity.ok().body(rolService.create(rol))
    }

    @GetMapping("/auth/roles-editar/{nombre}")
    fun editarForm(model: Model, @PathVariable(name = "nombre") nombre: String): String {
        model.addAttribute("rol", rolService.findOne(nombre))
        return "auth/rol/form"
    }

    @PostMapping("/auth/roles-editar/{nombreRol}")
    @ResponseBody
    fun editar(@Valid rol: Rol, @PathVariable(name = "nombreRol") nombreRol: String): ResponseEntity<String> {
        var rolAntiguo = rolService.findOne(nombreRol)
        return ResponseEntity.ok().body(rolService.update(rol.nombre, rolAntiguo))
    }

    @PostMapping("/auth/roles-eliminar/{nombre}")
    fun eliminar(@PathVariable(name = "nombre") nombre: String): ResponseEntity<String> {
        var rpta = rolService.delete(nombre)
        return ResponseEntity.ok().body(rpta)
    }

    @GetMapping("/auth/permisos/{rol}")
    fun permisos(model: Model, @PathVariable(name = "rol") rol: String): String {
        model.addAttribute("modulos", moduloService.findAll())
        model.addAttribute("esAdmin", rol == "ROLE_ADMIN")
        model.addAttribute("rol", rol)
        return "auth/rol/permisos"
    }
}