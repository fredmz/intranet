package web.intranet.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.Size

@Embeddable
data class PermisoId(
        @Column(length = 30, nullable = false, name = "rol_nombre")
        var rol: String = "",
        @Column(length = 45, nullable = false, name = "submodulo_id")
        var submoduloId: String = ""
): Serializable

@Entity
@Table(name = "auth_permiso")
data class Permiso(
        @EmbeddedId
        var id: PermisoId = PermisoId()
)

interface PermisoRepository: JpaRepository<Permiso, PermisoId>