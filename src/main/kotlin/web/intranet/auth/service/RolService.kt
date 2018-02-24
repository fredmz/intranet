package web.intranet.auth.service

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import web.intranet.auth.domain.Rol
import web.intranet.auth.domain.RolRepository
import web.intranet.auth.repository.filter.RolFilter

@Service
@Transactional
class RolService(
        val rolRepository: RolRepository
) {
    private val log = LoggerFactory.getLogger(RolService::class.java)

    fun create(rol: Rol): String {
        rol.nombre = rol.nombre.toUpperCase()
        if (rolRepository.findOne(rol.nombre) != null) {
            throw Exception("Ya existe un rol con ese nombre")
        }
        rolRepository.save(rol)
        return "Rol creado correctamente"
    }

    fun update(nombre: String, rolAntiguo: Rol): String {
        rolRepository.updateByNombre(nombre, rolAntiguo.nombre)
        return "Usuario actualizado correctamente de ${rolAntiguo.nombre} a $nombre"
    }

    @Transactional(readOnly = true)
    fun findAll(rolFilter: RolFilter, pageable: Pageable): Page<Rol> {
        log.info("UsuarioService findAll " + rolFilter)
        if (rolFilter.referencia.isNotBlank()) {
            return rolRepository.findByNombreContaining(rolFilter.referencia, pageable)
        }
        return rolRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    fun findAll(rolFilter: RolFilter): List<Rol> {
        log.info("UsuarioService findAll " + rolFilter)
        if (rolFilter.referencia.isNotBlank()) {
            return rolRepository.findByNombreContaining(rolFilter.referencia)
        }
        return rolRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findOne(nombre: String): Rol {
        return rolRepository.findOne(nombre)
    }

    fun delete(nombre: String): String {
        rolRepository.delete(nombre)
        return "Rol eliminado correctamente"
    }
}