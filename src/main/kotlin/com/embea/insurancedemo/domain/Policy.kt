package com.embea.insurancedemo.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class Policy (
    val policyId: UUID? = null,
    val startDate: LocalDate,
    val insuredPersons: List<InsuredPerson>,
    val totalPremium: BigDecimal = insuredPersons.sumOf { it.premium }
)

data class InsuredPerson (
    val id: UUID? = null,
    val firstName: String,
    val secondName: String,
    val premium: BigDecimal
)

object IdFactory {

    fun createId(): UUID = UUID.randomUUID()
}
