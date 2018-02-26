package web.intranet.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.*

@Entity
@Table(name = "auth_enlace")
class Enlace(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        @Column(length = 150)
        var nombre: String = "",
        @Column(length = 45, nullable = false)
        var submoduloId: String = "",
        @Column(length = 250, nullable = false)
        var descripcion: String = "",
        @Column(length = 10, nullable = false)
        var metodo: String = ""
)

@Repository
interface EnlaceRepository: JpaRepository<Enlace, Long>