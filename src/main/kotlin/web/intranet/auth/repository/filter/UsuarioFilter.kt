package web.intranet.auth.repository.filter

data class UsuarioFilter(
        var referencia: String = "",
        var roles: MutableList<String> = mutableListOf()
)