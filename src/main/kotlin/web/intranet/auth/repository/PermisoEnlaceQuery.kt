package web.intranet.auth.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import web.intranet.auth.domain.dto.MatcherDto
import web.intranet.utils.repository.Query
import javax.persistence.EntityManager

@Repository
class PermisoEnlaceQuery(
        val em: EntityManager
): Query()
{
    private val log = LoggerFactory.getLogger(PermisoEnlaceQuery::class.java)

    protected fun query(): String {
        return """
            SELECT arol.nombre rol, aenl.metodo, GROUP_CONCAT(aenl.id SEPARATOR ';') enlaces FROM auth_enlace aenl
            JOIN auth_permiso_enlace apen ON aenl.id = apen.enlace_id
            JOIN auth_rol arol ON apen.rol_nombre = arol.nombre
            GROUP BY apen.rol_nombre
        """
    }

    /**
     * Retorna la lista de enlaces de la aplicación de todos los usuarios para poder ser evaluada por spring security
     */
    fun findAll(): List<MatcherDto> {
        log.info("PermisoEnlaceQuery findAll")
        var query = em.createNativeQuery(query())
        var list = query.resultList.toList()
        var matcherDtos: MutableList<MatcherDto> = mutableListOf()
        list.forEach { it ->
            val lst = it as Array<out Any>
            matcherDtos.add(MatcherDto(lst[0] as String, lst[1] as String, lst[2] as String))
         }
        return matcherDtos
//        return query.resultList.toList() as List<MatcherDto>
    }
}