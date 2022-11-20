package com.embea.insurancedemo.dto

import com.embea.insurancedemo.controller.MoneySerializer
import com.embea.insurancedemo.domain.InsuredPerson
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class GetPolicyRequest(
    val policyId: UUID,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    val requestDate: LocalDate = LocalDate.now()
)

data class GetPolicyResponse(
    val policyId: UUID,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    val requestDate: LocalDate = LocalDate.now(),
    val insuredPersons: List<GetInsuredPersonResponse>,
    @JsonSerialize(using = MoneySerializer::class)
    val totalPremium: BigDecimal
)

data class GetInsuredPersonResponse(
    val id: UUID,
    val firstName: String,
    val secondName: String,
    @JsonSerialize(using = MoneySerializer::class)
    val premium: BigDecimal
)
