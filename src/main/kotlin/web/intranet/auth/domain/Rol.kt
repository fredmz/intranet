package web.intranet.auth.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "auth_rol")
data class Rol(
        @Id
        @Column(length = 30)
        @field:Size(min = 3, max = 30, message = "El rol debe estar comprendido entre 3 y 30 letras")
        var nombre: String = ""
)

interface RolRepository: JpaRepository<Rol, String> {
        fun findByNombreContaining(nombre: String, pageable: Pageable): Page<Rol>
        fun findByNombreContaining(nombre: String): List<Rol>

        @Modifying(clearAutomatically = true)
        @Query("update Rol r set r.nombre = :nombreNuevo where r.nombre=:nombreActual")
        fun updateByNombre(@Param("nombreNuevo") nombreNuevo: String, @Param("nombreActual") nombreActual: String)
}