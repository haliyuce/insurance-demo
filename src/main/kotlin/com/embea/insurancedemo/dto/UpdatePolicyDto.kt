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
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Future

data class UpdatePolicyRequest (
    val policyId: UUID,
    @field:Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    val effectiveDate: LocalDate,
    @field:Valid
    val insuredPersons: List<@Valid UpdateInsuredPersonRequest>,
    @JsonSerialize(using = MoneySerializer::class)
    val totalPremium: BigDecimal
)

data class UpdateInsuredPersonRequest(
    val id: UUID?,
    val firstName: String,
    val secondName: String,
    @JsonDeserialize(using = MoneyDeserializer::class)
    @field:DecimalMin(value = "0.0", inclusive = false)
    val premium: BigDecimal
)

data class UpdatePolicyResponse(
    val policyId: UUID,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    val effectiveDate: LocalDate,
    val insuredPersons: List<UpdateInsuredPersonResponse>,
    @JsonSerialize(using = MoneySerializer::class)
    val totalPremium: BigDecimal
)

data class UpdateInsuredPersonResponse(
    val id: UUID,
    val firstName: String,
    val secondName: String,
    @JsonSerialize(using = MoneySerializer::class)
    val premium: BigDecimal
)
