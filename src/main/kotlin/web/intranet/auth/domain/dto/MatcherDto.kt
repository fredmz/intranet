package web.intranet.auth.domain.dto

import org.springframework.http.HttpMethod

data class MatcherDto(
    var enlaces: String = "",
    var rol: String = "",
    var metodo: String = ""
) {
    fun httpMethod(): HttpMethod {
        if (metodo == "POST") {
            return HttpMethod.POST
        }
        return HttpMethod.GET
    }

    fun methodIsANY(): Boolean {
        return metodo == "ANY"
    }

    fun arrayEnlaces(): Array<String> {
        return enlaces.split(";").toTypedArray()
    }
}