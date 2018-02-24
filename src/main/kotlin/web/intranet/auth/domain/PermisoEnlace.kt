package web.intranet.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

data class PermisoEnlaceId(
        @Column(length = 30, nullable = false, name = "rol_nombre")
        var rol: String = "",
        @Column(nullable = false, name = "enlace_id")
        var enlaceId: Long = 0
): Serializable

@Entity
@Table(name = "auth_permiso_enlace")
data class PermisoEnlace(
        @EmbeddedId
        var id: PermisoEnlaceId = PermisoEnlaceId()
)

interface PermisoEnlaceRepository: JpaRepository<PermisoEnlace, PermisoEnlaceId>