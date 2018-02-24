package web.intranet.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "auth_modulo")
data class Modulo(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(nullable = false, length = 100)
        @field:Size(max = 100)
        var nombre: String = "",

        @Column(nullable = false, length = 45)
        var icono: String = "",

        @Column(nullable = false)
        var orden: Int = 0
)

@Repository
interface ModuloRepository: JpaRepository<Modulo, Long>