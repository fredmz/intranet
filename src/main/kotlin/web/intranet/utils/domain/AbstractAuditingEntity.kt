package web.intranet.utils.domain

import org.hibernate.envers.Audited
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import java.time.Instant
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.CreatedBy
import javax.persistence.Column


@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener::class)
class AbstractAuditingEntity {
    @CreatedBy
    @Column(name = "creado_por", nullable = false, length = 45, updatable = false)
    private val creadoPor: String = ""

    @CreatedDate
    @Column(name = "fecha_creado", nullable = false, updatable = false)
    private val fechaCreado = Instant.now()

    @LastModifiedBy
    @Column(name = "modificado_por", length = 45)
    private val modificadoPor: String = ""

    @LastModifiedDate
    @Column(name = "fecha_modificado")
    private val fechaModificado = Instant.now()
}