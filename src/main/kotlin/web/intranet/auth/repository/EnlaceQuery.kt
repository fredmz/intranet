package web.intranet.auth.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import web.intranet.auth.domain.dto.EnlaceDto
import web.intranet.utils.repository.Query
import java.math.BigInteger
import javax.persistence.EntityManager

@Repository
class EnlaceQuery(
        private val em: EntityManager
): Query()
{
    private val log = LoggerFactory.getLogger(EnlaceQuery::class.java)

    protected fun query(submoduloId: String, rol: String): String {
        return """select aenl.id, aenl.nombre, aenl.descripcion, aenl.metodo, apen.rol_nombre from auth_enlace aenl
                    left join auth_permiso_enlace apen on (aenl.id = apen.enlace_id and apen.rol_nombre='$rol')
                    where aenl.submodulo_id = '$submoduloId'
                    order by aenl.metodo, aenl.descripcion;"""
    }

    fun findAllBySubmoduloIdAndRol(submoduloId: String, rol: String): List<EnlaceDto> {
        log.info("EnlaceQuery findAll submoduloId: $submoduloId y rol: $rol")
        var q = query(submoduloId, rol)
        var query = em.createNativeQuery(q)
        var list = query.resultList.toList()
        var enlaces: MutableList<EnlaceDto> = mutableListOf()
        list.forEach { it ->
            val lst = it as Array<out Any>
            var rolName = lst[4] as String?
            if (rolName == null && rol == "ROLE_ADMIN") {
                rolName = rol
            }
            enlaces.add(EnlaceDto((lst[0] as BigInteger).toLong(), lst[1] as String, lst[2] as String, lst[3] as String, rolName))
        }
        return enlaces
    }
}