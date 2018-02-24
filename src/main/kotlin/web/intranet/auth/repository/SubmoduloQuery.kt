package web.intranet.auth.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import web.intranet.auth.domain.Usuario
import web.intranet.auth.domain.dto.ModuloDto
import web.intranet.auth.domain.dto.SubmoduloDto
import web.intranet.utils.repository.Query
import javax.persistence.EntityManager

@Repository
class SubmoduloQuery(
        private val em: EntityManager
): Query()
{
    private val log = LoggerFactory.getLogger(SubmoduloQuery::class.java)

    protected fun query(moduloId: Long, rol: String): String {
        return """SELECT asub.id submodulo_id, asub.nombre submodulo_nombre, asub.icono submodulo_icono, asub.enlace submodulo_enlace, aper.rol_nombre submodulo_rol FROM
                    auth_submodulo asub
                    LEFT JOIN auth_permiso aper ON (asub.id = aper.submodulo_id AND aper.rol_nombre = '$rol')
                    WHERE asub.modulo_id = $moduloId
                    ORDER BY asub.orden"""
    }

    fun findAllByModuloIdAndRol(moduloId: Long, rol: String): List<SubmoduloDto> {
        log.info("SubmoduloQuery findAll moduloId: $moduloId y rol: $rol")
        var q = query(moduloId, rol)
        var query = em.createNativeQuery(q)
        var list = query.resultList.toList()
        var submodulos: MutableList<SubmoduloDto> = mutableListOf()
        list.forEach { it ->
            val lst = it as Array<out Any>
            var rolName = lst[4] as String?
            if (rolName == null && rol == "ROLE_ADMIN") {
                rolName = rol
            }
            submodulos.add(SubmoduloDto(lst[0] as String, lst[1] as String, lst[2] as String, lst[3] as String, rolName))
        }
        return submodulos
    }
}