package web.intranet.auth.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import web.intranet.auth.domain.dto.MenuItemDto
import web.intranet.utils.repository.Query
import javax.persistence.EntityManager

@Repository
class MenuItemQuery(
        val em: EntityManager
): Query()
{
    private val log = LoggerFactory.getLogger(MenuItemQuery::class.java)

    protected fun query(rol: String): String {
        var whereRol = ""
        if (rol != "ROLE_ADMIN") {
            whereRol = """
                JOIN auth_permiso aper ON asmo.id = aper.submodulo_id
                WHERE aper.rol_nombre = '${rol}'
            """
        }
        return """
            SELECT DISTINCT amod.nombre, amod.icono,
            GROUP_CONCAT(CONCAT(asmo.nombre, ';' , asmo.enlace, ';', asmo.icono) SEPARATOR '|') subitems
            FROM auth_modulo amod
            JOIN auth_submodulo asmo ON amod.id = asmo.modulo_id
            $whereRol
            GROUP BY amod.id
            ORDER BY amod.orden, asmo.orden
        """
    }

    /**
     * Retorna la lista de enlaces del menu de la aplicación, en caso sea ROLE_ADMIN enviará la lista completa
     */
    fun findAll(rol: String): List<MenuItemDto> {
        log.info("MenuItemQuery findAll for: " + rol)
        var query = em.createNativeQuery(query(rol))
        var list = query.resultList.toList()
        var menuItems: MutableList<MenuItemDto> = mutableListOf()
        list.forEach { it ->
            val lst = it as Array<out Any>
            var item = MenuItemDto(lst[0] as String, "", lst[1] as String)
            var subitems = (lst[2] as String).split("|")
            subitems.forEach { subit ->
                val sublst = subit.split(';')
                item.subMenuItems.add(MenuItemDto(sublst[0], sublst[1], sublst[2]))
            }
            menuItems.add(item)
         }
        return menuItems
    }
}