package com.embea.insurancedemo.controller

import com.embea.insurancedemo.PolicyFactory
import com.embea.insurancedemo.domain.InsuredPerson
import com.embea.insurancedemo.service.PolicyService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [PolicyController::class])
internal class PolicyControllerTest {

    @MockBean
    lateinit var policyService: PolicyService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun create_works() {

        // given
        val createdPolicy = PolicyFactory.createPolicy()
        `when`(policyService.savePolicy(any())).thenReturn(createdPolicy)

        // when
        mockMvc.perform(
            post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PolicyFactory.getCreatePolicyRequestStr())
        )
            .andExpect(status().isCreated)
            .andExpect(
                content().json(
                    PolicyFactory.getCreatePolicyResponseStr(
                        createdPolicy.policyId,
                        createdPolicy.insuredPersons[0].id,
                        createdPolicy.insuredPersons[1].id
                    )
                )
            )

        // then
        PolicyFactory.createPolicy(
            policyId = createdPolicy.policyId,
            insuredPersonId1 = createdPolicy.insuredPersons[0].id,
            insuredPersonId2 = createdPolicy.insuredPersons[1].id
        )
    }

    @ParameterizedTest
    @MethodSource("invalidCreatePolicyRequests")
    fun create_fails_when_request_is_invalid(policyRequestStr: String) {

        // given

        // when
        mockMvc.perform(
            post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(policyRequestStr)
        )
            .andExpect(status().isBadRequest)

        // then
        verifyNoInteractions(policyService)
    }

    @Test
    fun update_works() {

        //given
        val existingPolicy = PolicyFactory.createPolicy()
        val updatedPolicy = existingPolicy.copy(
            insuredPersons = listOf(
                existingPolicy.insuredPersons[0],
                InsuredPerson(
                    firstName = "Will",
                    secondName = "Smith",
                    premium = BigDecimal("12.90")
                )
            )
        )
        `when`(policyService.updatePolicy(any())).thenReturn(updatedPolicy)

        //when
        mockMvc.perform(
            put("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    PolicyFactory.getUpdatePolicyRequestStr(
                        policyId = existingPolicy.policyId,
                        insuredPersonId1 = existingPolicy.insuredPersons[0].id,
                    )
                )
        )
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    PolicyFactory.getUpdatePolicyResponseStr(
                        updatedPolicy.policyId,
                        updatedPolicy.insuredPersons[0].id,
                        updatedPolicy.insuredPersons[1].id
                    )
                )
            )

        //then
    }

    @ParameterizedTest
    @MethodSource("invalidUpdatePolicyRequests")
    fun update_fails_when_request_is_invalid(policyRequestStr: String) {

        // given

        // when
        mockMvc.perform(
            put("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(policyRequestStr)
        )
            .andExpect(status().isBadRequest)

        // then
        verifyNoInteractions(policyService)
    }

    @Test
    fun get_works() {

        //given
        val requestDate = LocalDate.now().minusDays(1)
        val policy = PolicyFactory.createPolicy()
        `when`(policyService.getPolicy(any(), any())).thenReturn(policy)

        //when
        mockMvc.perform(
            get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    PolicyFactory.getGetRequestStr(
                        policyId = policy.policyId,
                        requestDate = requestDate
                    )
                )
        )
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    PolicyFactory.getGetResponseStr(policy.policyId, requestDate = requestDate)
                )
            )

        //then
    }

    @Test
    fun get_works_when_requestDate_does_not_exist() {

        //given
        val policy = PolicyFactory.createPolicy()
        `when`(policyService.getPolicy(any(), any())).thenReturn(policy)
        val today = LocalDate.now()

        //when
        mockMvc.perform(
            get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    PolicyFactory.getGetRequestStr(
                        policyId = policy.policyId
                    )
                )
        )
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    PolicyFactory.getGetResponseStr(policy.policyId, requestDate = today)
                )
            )

        //then
    }

    private fun invalidCreatePolicyRequests(): Stream<Arguments?>? {
        return Stream.of(
            Arguments.of(PolicyFactory.getCreatePolicyRequestStr(premiumForInsurePerson1 = BigDecimal.ZERO)),
            Arguments.of(PolicyFactory.getCreatePolicyRequestStr(premiumForInsurePerson2 = BigDecimal.ZERO)),
            Arguments.of(PolicyFactory.getCreatePolicyRequestStr(premiumForInsurePerson1 = BigDecimal("-1"))),
            Arguments.of(PolicyFactory.getCreatePolicyRequestStr(premiumForInsurePerson2 = BigDecimal("-1"))),
            Arguments.of(PolicyFactory.getCreatePolicyRequestStr(startDate = LocalDate.now().minusDays(1)))
        )
    }

    private fun invalidUpdatePolicyRequests(): Stream<Arguments?>? {
        return Stream.of(
            Arguments.of(PolicyFactory.getUpdatePolicyRequestStr(premiumForInsurePerson1 = BigDecimal.ZERO)),
            Arguments.of(PolicyFactory.getUpdatePolicyRequestStr(premiumForInsurePerson2 = BigDecimal.ZERO)),
            Arguments.of(PolicyFactory.getUpdatePolicyRequestStr(premiumForInsurePerson1 = BigDecimal("-1"))),
            Arguments.of(PolicyFactory.getUpdatePolicyRequestStr(premiumForInsurePerson2 = BigDecimal("-1"))),
            Arguments.of(PolicyFactory.getUpdatePolicyRequestStr(effectiveDate = LocalDate.now().minusDays(1)))
        )
    }
}
