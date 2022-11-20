package com.embea.insurancedemo.entity

import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID
import javax.persistence.*

@Entity
class PolicyEntity(
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    var id: UUID,
    val startDate: LocalDate,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "policy_id")
    val insuredPersons: List<InsuredPersonEntity>
)

@Entity
class InsuredPersonEntity(
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    var id: UUID = UUID.randomUUID(),
    var firstName: String,
    var secondName: String,
    var premium: BigDecimal
)