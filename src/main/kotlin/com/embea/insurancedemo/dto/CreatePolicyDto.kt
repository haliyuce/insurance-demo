package com.embea.insurancedemo.dto

import com.embea.insurancedemo.controller.MoneyDeserializer
import com.embea.insurancedemo.controller.MoneySerializer
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID
import javax.validation.Valid
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Future
import javax.validation.constraints.Min

data class CreatePolicyRequest(
    @field:Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    val startDate: LocalDate,
    @field:Valid
    val insuredPersons: List<@Valid CreateInsuredPerson>
)

data class CreateInsuredPerson(
    val firstName: String,
    val secondName: String,
    @JsonDeserialize(using = MoneyDeserializer::class)
    @field:DecimalMin(value = "0.0", inclusive = false)
    val premium: BigDecimal
)

data class CreatePolicyResponse(
    val policyId: UUID,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    val startDate: LocalDate,
    val insuredPersons: List<CreateInsuredPersonResponse>,
    @JsonSerialize(using = MoneySerializer::class)
    val totalPremium: BigDecimal
)

data class CreateInsuredPersonResponse(
    val id: UUID,
    val firstName: String,
    val secondName: String,
    @JsonSerialize(using = MoneySerializer::class)
    val premium: BigDecimal
)
