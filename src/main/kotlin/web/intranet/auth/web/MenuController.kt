package web.intranet.auth.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MenuController {
    @GetMapping("/menu", name = "menu")
    fun index(model: Model): String {
        return "menu"
    }
}