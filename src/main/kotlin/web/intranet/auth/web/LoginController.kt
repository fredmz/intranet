package web.intranet.auth.web

import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import web.intranet.auth.service.UsuarioService
import java.security.Principal
import javax.servlet.http.HttpServletRequest


@Controller
class LoginController(
        val usuarioService: UsuarioService,
        val env: Environment
) {
    @GetMapping("/", name = "login")
    fun login(model: Model, request: HttpServletRequest): String {
        if (request.userPrincipal != null) {
            return "redirect:/menu"
        }
        var error = (request.parameterMap.containsKey("error"))
        model.addAttribute("loginError", error)
        model.addAttribute("nameApp", env.getProperty("spring.application.name"))
        return "login"
    }

    @GetMapping("/cambiar-clave", name = "cambiar-clave")
    fun cambioDeClave(): String {
        return "password"
    }

    @PostMapping("/cambiar-clave", name = "cambiar-clave")
    @ResponseBody
    fun cambiarClave(claveActual: String, claveNueva: String, claveNueva2: String, principal: Principal): ResponseEntity<String> {
        val rspt = usuarioService.cambiarClave(claveActual, claveNueva, claveNueva2, usuarioService.findByLogin(principal.name).get())
        return ResponseEntity.ok().body(rspt)
    }

    @GetMapping("/eliminar-form")
    fun eliminarForm(): String {
        return "eliminar"
    }

    @GetMapping("/recuperar-clave")
    fun recuperarClave(): String {
        return "recuperar-clave"
    }

    @PostMapping("/recuperar-clave")
    fun recuperarClave(correo: String, model: Model): ResponseEntity<String> {
        return ResponseEntity.ok().body(usuarioService.recuperarClave(correo, model))
    }

    @GetMapping("/resetear-clave")
    fun resetearClave(@RequestParam(name = "resetKey", required = false) resetKey: String?,
                      @RequestParam(name = "error", required = false) error: String?,
                      model: Model): String {
        var key = ""
        if (resetKey != null) {
            key = resetKey
            var usuario = usuarioService.findByKeyReset(key)
            var validKey = (usuario != null && usuario.puedeCambiarLaClavePorReseteo())
            model.addAttribute("validKey", validKey)
            model.addAttribute("usuario", usuario)
        } else {
            model.addAttribute("validKey", false)
        }
        model.addAttribute("resetKey", key)
        model.addAttribute("isPost", false)
        model.addAttribute("error", error)
        return "resetear-clave"
    }

    @PostMapping("/resetear-clave")
    fun resetearClave(claveNueva: String, claveNueva2: String, resetKey: String, model: Model): String {
        if (claveNueva != claveNueva2) {
            return "redirect:/resetear-clave?resetKey=$resetKey&error=1"
        }
        model.addAttribute("msg", usuarioService.resetearClave(claveNueva, claveNueva2, resetKey))
        model.addAttribute("isPost", true)
        return "resetear-clave"
    }
}