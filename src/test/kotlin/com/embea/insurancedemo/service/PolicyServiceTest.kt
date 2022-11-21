package com.embea.insurancedemo.service

import com.embea.insurancedemo.PolicyFactory
import com.embea.insurancedemo.domain.InsuredPerson
import com.embea.insurancedemo.repository.PolicyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import java.math.BigDecimal
import java.time.LocalDate

@DataJpaTest
@Import(PolicyService::class)
internal class PolicyServiceTest {

    @Autowired
    lateinit var policyRepository: PolicyRepository

    @Autowired
    lateinit var policyService: PolicyService

    @Test
    fun savePolicy_works() {

        //given
        val policyToSave = PolicyFactory.createPolicy()

        //when
        val actual = policyService.savePolicy(policyToSave)

        //then
        val persistedOpt = policyRepository.findById(actual.policyId)
        assertThat(persistedOpt).isPresent
        val persisted = persistedOpt.get()
        val expected = PolicyFactory.createPolicy(
            policyId = persisted.id,
            insuredPersonId1 = persisted.insuredPersons.first { it.firstName == actual.insuredPersons[0].firstName}.id,
            insuredPersonId2 = persisted.insuredPersons.first { it.firstName == actual.insuredPersons[1].firstName}.id
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun updatePolicy_works() {

        //given
        val policy = PolicyFactory.createPolicy()
        policyRepository.save(PolicyMapper.toEntity(policy))

        val newPolicy = policy.copy(
            insuredPersons = listOf(
                policy.insuredPersons[0],
                InsuredPerson(
                    firstName = "Will",
                    secondName =  "Smith",
                    premium = BigDecimal("12.90")
                ),
            )
        )

        //when
        val actual = policyService.updatePolicy(newPolicy)

        //then
        assertThat(actual).isNotNull
        val updatedPolicyEntity = policyRepository.findById(actual!!.policyId)
        assertThat(updatedPolicyEntity).isPresent
        assertThat(actual).isEqualTo(PolicyMapper.fromEntity(updatedPolicyEntity.get()))
    }

    @Test
    fun getPolicy_works() {

        //given
        val policy = PolicyFactory.createPolicy(startDate = LocalDate.now().minusDays(1))
        policyRepository.save(PolicyMapper.toEntity(policy))

        //when
        val actual = policyService.getPolicy(policy.policyId, LocalDate.now())

        //then
        assertThat(actual).isEqualTo(policy)
    }
}
