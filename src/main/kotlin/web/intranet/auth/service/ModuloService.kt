package web.intranet.auth.service

import org.springframework.stereotype.Service
import web.intranet.auth.domain.Modulo
import web.intranet.auth.domain.ModuloRepository

@Service
class ModuloService(
        val moduloRepository: ModuloRepository
) {
    fun findAll(): List<Modulo> {
        return moduloRepository.findAll()
    }
}