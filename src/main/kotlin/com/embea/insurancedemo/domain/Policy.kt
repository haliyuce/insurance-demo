package com.embea.insurancedemo.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class Policy (
    val policyId: UUID = UUID.randomUUID(),
    val startDate: LocalDate,
    val insuredPersons: List<InsuredPerson>,
) {
    fun calculateTotalPremium() = insuredPersons.sumOf { it.premium }
}

data class InsuredPerson (
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val secondName: String,
    val premium: BigDecimal
)
