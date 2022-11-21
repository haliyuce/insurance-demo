package com.embea.insurancedemo.controller.mapper

import com.embea.insurancedemo.domain.InsuredPerson
import com.embea.insurancedemo.domain.Policy
import com.embea.insurancedemo.dto.*
import java.math.RoundingMode
import java.time.LocalDate
import java.util.*
import kotlin.streams.toList

object PolicyMapper {

    fun createPolicyFromCreatePolicyRequest(createPolicyRequest: CreatePolicyRequest): Policy {
        return Policy(
            startDate = createPolicyRequest.startDate,
            insuredPersons = createPolicyRequest.insuredPersons.stream()
                .map {
                    InsuredPerson(
                        firstName = it.firstName,
                        secondName = it.secondName,
                        premium = it.premium
                    )
                }.toList()
        )
    }

    fun mapCreatePolicyResponseFromPolicy(policy: Policy): CreatePolicyResponse {
        return CreatePolicyResponse(
            policyId = policy.policyId,
            startDate = policy.startDate,
            insuredPersons = policy.insuredPersons.stream()
                .map {
                    CreateInsuredPersonResponse(
                        id = it.id,
                        firstName = it.firstName,
                        secondName = it.secondName,
                        premium = it.premium
                    )
                }.toList(),
            totalPremium = policy.calculateTotalPremium().setScale(2, RoundingMode.HALF_UP)
        )
    }

    fun createPolicyFromUpdateDto(updatePolicyRequest: UpdatePolicyRequest): Policy {
        return Policy(
            policyId = updatePolicyRequest.policyId,
            startDate = updatePolicyRequest.effectiveDate,
            insuredPersons = updatePolicyRequest.insuredPersons.stream()
                .map {
                    InsuredPerson(
                        id = it.id ?: UUID.randomUUID(),
                        firstName = it.firstName,
                        secondName = it.secondName,
                        premium = it.premium
                    )
                }.toList()
        )
    }

    fun mapUpdatePolicyResponseFromPolicy(policy: Policy): UpdatePolicyResponse {
        return UpdatePolicyResponse(
            policyId = policy.policyId,
            effectiveDate = policy.startDate,
            insuredPersons = policy.insuredPersons.stream()
                .map {
                    UpdateInsuredPersonResponse(
                        id = it.id,
                        firstName = it.firstName,
                        secondName = it.secondName,
                        premium = it.premium
                    )
                }.toList(),
            totalPremium = policy.calculateTotalPremium().setScale(2, RoundingMode.HALF_UP)
        )
    }

    fun mapGetPolicyResponseFromPolicy(policy: Policy, requestDate: LocalDate): GetPolicyResponse {
        return GetPolicyResponse(
            policyId = policy.policyId,
            requestDate = requestDate,
            insuredPersons = policy.insuredPersons.stream()
                .map {
                    GetInsuredPersonResponse(
                        id = it.id,
                        firstName = it.firstName,
                        secondName = it.secondName,
                        premium = it.premium
                    )
                }.toList(),
            totalPremium = policy.calculateTotalPremium().setScale(2, RoundingMode.HALF_UP)
        )

    }
}