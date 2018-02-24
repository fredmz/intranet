package web.intranet.auth.service

import org.springframework.stereotype.Service
import web.intranet.auth.domain.dto.MatcherDto
import web.intranet.auth.repository.PermisoEnlaceQuery

@Service
class PermisoEnlaceService(
        val permisoEnlaceQuery: PermisoEnlaceQuery
) {
    fun findAll(): List<MatcherDto> {
        return permisoEnlaceQuery.findAll()
    }
}