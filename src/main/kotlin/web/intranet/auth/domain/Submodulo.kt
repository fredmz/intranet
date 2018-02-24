package web.intranet.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "auth_submodulo")
data class Submodulo(
        @Id
        @Column(length = 45)
        var id: String = "",

        @Column(nullable = false)
        var moduloId: Long = 0,

        @Column(nullable = false, length = 100)
        @field:Size(max = 100)
        var nombre: String = "",

        @Column(nullable = false, length = 50)
        @field:Size(max = 50)
        var icono: String = "",

        @Column(nullable = false, length = 150)
        @field:Size(max = 150)
        var enlace: String = "",

        @Column(nullable = false)
        var orden: Int = 0
)

@Repository
interface SubmoduloRepository: JpaRepository<Submodulo, String> {
        fun findByModuloIdOrderByOrdenAsc(moduloId: Long): List<Submodulo>
}