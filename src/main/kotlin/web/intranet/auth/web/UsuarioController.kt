package web.intranet.auth.web

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import web.intranet.auth.domain.Usuario
import web.intranet.auth.domain.dto.UsuarioDto
import web.intranet.auth.repository.filter.RolFilter
import web.intranet.auth.repository.filter.UsuarioFilter
import web.intranet.auth.service.RolService
import web.intranet.auth.service.UsuarioService
import web.intranet.auth.web.utils.Pager
import web.intranet.utils.service.ExcelService
import web.intranet.utils.web.api.PaginationUtils
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@Controller
class UsuarioController(
        val usuarioService: UsuarioService,
        val rolService: RolService,
        val excelService: ExcelService
) {
    private val log = LoggerFactory.getLogger(UsuarioController::class.java)

    @GetMapping("/auth/usuarios")
    fun listar(model: Model, usuarioFilter: UsuarioFilter, pageable: Pageable): String {
        model.addAttribute("roles", rolService.findAll(RolFilter()))
        model.addAttribute("filtro", usuarioFilter)
        log.info("pageable: " + pageable)
        var lista = usuarioService.findAll(usuarioFilter, pageable)
        model.addAttribute("lista", lista)
        model.addAttribute("pager", Pager(lista.totalPages, lista.number))
        return "auth/usuario/listar"
    }

    @GetMapping("/api/auth/usuarios")
    @ResponseBody
    fun listar(usuarioFilter: UsuarioFilter, pageable: Pageable): ResponseEntity<List<Usuario>> {
        log.info("UsuarioResource listar: " + usuarioFilter)
        var page = usuarioService.findAll(usuarioFilter, pageable)
        return ResponseEntity(
                page.content,
                PaginationUtils().generatePaginationHttpHeaders(page, "/innerApi/auth/usuarios"),
                HttpStatus.OK
        )
    }

    @GetMapping("/auth/usuarios-nuevo")
    fun crearForm(model: Model): String{
        model.addAttribute("usuario", Usuario())
        model.addAttribute("roles", rolService.findAll(RolFilter()))
        return "auth/usuario/form"
    }

    @PostMapping("/auth/usuarios-nuevo")
    @ResponseBody
    fun crear(usuario: Usuario): ResponseEntity<String> {
        return ResponseEntity.ok().body(usuarioService.create(usuario))
    }

    @GetMapping("/auth/usuarios-editar/{login}")
    fun editarForm(model: Model, @PathVariable(name = "login") login: String): String{
        model.addAttribute("usuario", usuarioService.findByLogin(login).get())
        model.addAttribute("roles", rolService.findAll(RolFilter()))
        return "auth/usuario/form"
    }

    @PostMapping("/auth/usuarios-editar/{login}")
    @ResponseBody
    fun editar(@Valid usuarioVM: UsuarioDto, @PathVariable(name = "login") login: String): ResponseEntity<String> {
        var usuario = usuarioService.findByLogin(login).get()
        usuario.fromUsuarioVM(usuarioVM)
        return ResponseEntity.ok().body(usuarioService.update(usuario))
    }

    @PostMapping("/auth/usuarios-eliminar/{login}")
    fun eliminar(@PathVariable(name = "login") login: String): ResponseEntity<String> {
        var rpta = usuarioService.delete(usuarioService.findByLogin(login).get())
        return ResponseEntity.ok().body(rpta)
    }

    @GetMapping("/auth/usuarios-exportar")
    fun exportar(response: HttpServletResponse, usuarioFilter: UsuarioFilter) {
        excelService.generar(response, mutableListOf("Nombre", "Correo", "Rol", "Habilitado"),
                "login, correo, rolNombre, habilitado",
                usuarioService.findAll(usuarioFilter),
                "Usuarios")
    }
}