package web.intranet.auth.repository

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import web.intranet.auth.domain.Usuario
import web.intranet.auth.repository.filter.UsuarioFilter
import web.intranet.utils.repository.Query
import javax.persistence.EntityManager

@Repository
class UsuarioQuery(
        val em: EntityManager
): Query()
{
    private val log = LoggerFactory.getLogger(UsuarioQuery::class.java)

    protected fun query(usuarioFilter: UsuarioFilter): String {
        var filters = ""
        if (usuarioFilter.referencia.isNotBlank()) {
            filters += " AND " + like(usuarioFilter.referencia, arrayOf("usu.login", "usu.correo", "usu.rol_nombre"))
        }
        if (usuarioFilter.roles.isNotEmpty()) {
            filters += " AND usu.rol_nombre IN ('" + usuarioFilter.roles.joinToString("','") + "')"
        }
        return """SELECT usu.* FROM auth_usuario usu
                 WHERE usu.id > 0
                $filters GROUP BY usu.id"""
    }

    fun findAll(usuarioFilter: UsuarioFilter, pageable: Pageable): Page<Usuario> {
        log.info("UsuarioQuery findAll: " + usuarioFilter)
        var q = query(usuarioFilter)
        var query = em.createNativeQuery(q, Usuario::class.java)
        query.firstResult = pageable.offset
        query.maxResults = pageable.pageSize
//        var usuarios = em.createNativeQuery(pagedQuery(q, pageable), Usuario::class.java).resultList.toList() as List<Usuario>
        var usuarios = query.resultList.toList() as List<Usuario>
        var total = em.createNativeQuery(totalQuery(q)).singleResult.toString().toLong()
        return PageImpl(usuarios, pageable, total)
    }

    fun findAll(usuarioFilter: UsuarioFilter): List<Usuario> {
        log.info("UsuarioQuery findAll: " + usuarioFilter)
        var q = query(usuarioFilter)
        var query = em.createNativeQuery(q, Usuario::class.java)
        return query.resultList.toList() as List<Usuario>
    }
}