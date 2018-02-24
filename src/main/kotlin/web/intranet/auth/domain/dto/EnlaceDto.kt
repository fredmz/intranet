package web.intranet.auth.domain.dto

data class EnlaceDto(
        var id: Long = 0,
        var nombre: String = "",
        var descripcion: String = "",
        var metodo: String = "",
        /**
         * Si tiene un nombre significa que ese usuario tiene acceso a este enlace
         */
        var rol: String? = null
)