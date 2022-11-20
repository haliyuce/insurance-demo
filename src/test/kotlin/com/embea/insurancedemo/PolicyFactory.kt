package com.embea.insurancedemo

import com.embea.insurancedemo.domain.IdFactory
import com.embea.insurancedemo.domain.InsuredPerson
import com.embea.insurancedemo.domain.Policy
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object PolicyFactory {

    private val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun getCreatePolicyRequestStr(
        startDate: LocalDate = LocalDate.now().plusDays(1),
        premiumForInsurePerson1: BigDecimal = BigDecimal("12.90"),
        premiumForInsurePerson2: BigDecimal = BigDecimal("15.90")
    ): String {
        return """
            {
                "startDate": "${startDate.format(dateFormat)}",
                "insuredPersons": [
                    {
                        "firstName": "Jane",
                        "secondName": "Johnson",
                        "premium": ${premiumForInsurePerson1.setScale(2, RoundingMode.HALF_UP)}
                    },
                    {
                        "firstName": "Jack",
                        "secondName": "Doe",
                        "premium": ${premiumForInsurePerson2.setScale(2, RoundingMode.HALF_UP)}
                    }
                ]
            }
        """.trimIndent()
    }

    fun getCreatePolicyResponseStr(policyId: UUID, person1Id: UUID, person2Id: UUID): String {
        return """
            {
                "policyId": "$policyId",
                "startDate": "${LocalDate.now().plusDays(1).format(dateFormat)}",
                "insuredPersons": [
                    {
                        "id": $person1Id,
                        "firstName": "Jane",
                        "secondName": "Johnson",
                        "premium": 12.90
                    },
                    {
                        "id": $person2Id,
                        "firstName": "Jack",
                        "secondName": "Doe",
                        "premium": 15.90
                    }
                ],
                "totalPremium": 28.80
            }
        """.trimIndent()
    }

    fun createPolicy(
        policyId: UUID? = IdFactory.createId(),
        insuredPersonId1: UUID? = IdFactory.createId(),
        insuredPersonId2: UUID? = IdFactory.createId()
    ) = Policy(
        policyId = policyId,
        startDate = LocalDate.now().plusDays(1),
        insuredPersons = listOf(
            InsuredPerson(
                insuredPersonId1,
                "Jane",
                "Johnson",
                BigDecimal("12.9")
            ),
            InsuredPerson(
                insuredPersonId2,
                "Jack",
                "Doe",
                BigDecimal("15.9")
            ),
        ),
        totalPremium = BigDecimal("28.8")
    )

    fun getUpdatePolicyRequestStr(
        policyId: UUID = UUID.randomUUID(),
        insuredPersonId1: UUID = UUID.randomUUID(),
        premiumForInsurePerson1: BigDecimal = BigDecimal("12.90"),
        premiumForInsurePerson2: BigDecimal = BigDecimal("12.90"),
        effectiveDate: LocalDate = LocalDate.now().plusDays(1)
    ): String {

        return """
            {
                "policyId": "$policyId",
                "effectiveDate":"${effectiveDate.format(dateFormat)}",
                "insuredPersons": [
                    {
                        "id": "$insuredPersonId1",
                        "firstName": "Jane",
                        "secondName": "Johnson",
                        "premium": $premiumForInsurePerson1
                    },
                    {
                        "firstName": "Will",
                        "secondName": "Smith",
                        "premium": $premiumForInsurePerson2
                    }
                ],
                "totalPremium": ${
            premiumForInsurePerson1.add(premiumForInsurePerson2).setScale(2, RoundingMode.HALF_UP)
        }
            }
        """.trimIndent()
    }

    fun getUpdatePolicyResponseStr(policyId: UUID, insuredPersonId1: UUID, insuredPersonId2: UUID): String {

        return """
            {
                "policyId": "$policyId",
                "effectiveDate": "${LocalDate.now().plusDays(1).format(dateFormat)}",
                "insuredPersons": [
                    {
                        "id": "$insuredPersonId1",
                        "firstName": "Jane",
                        "secondName": "Johnson",
                        "premium": 12.90
                    },
                    {
                        "id": "$insuredPersonId2",
                        "firstName": "Will",
                        "secondName": "Smith",
                        "premium": 12.90
                    }
                ],
                "totalPremium": 25.80
            }
        """.trimIndent()
    }

    fun getGetRequestStr(policyId: UUID, requestDate: LocalDate? = null): String {

        var requestDateStr = ""
        if (requestDate != null) {
            requestDateStr = """
                "requestDate": "${requestDate.format(dateFormat)}",
            """.trimIndent()
        }

        return """
            {
                $requestDateStr
                "policyId": "$policyId"
            }
        """.trimIndent()
    }

    fun getGetResponseStr(id: UUID, requestDate: LocalDate = LocalDate.now()): String {
        return """
            {
                "policyId": "$id",
                "requestDate": "${requestDate.format(dateFormat)}",
                "insuredPersons": [
                    {
                        "firstName": "Jane",
                        "secondName": "Johnson",
                        "premium": 12.90
                    },
                    {
                        "firstName": "Jack",
                        "secondName": "Doe",
                        "premium": 15.90
                    }
                ],
                "totalPremium": 28.80
            }
        """.trimIndent()
    }
}
