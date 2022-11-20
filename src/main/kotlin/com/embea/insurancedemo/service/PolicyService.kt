package com.embea.insurancedemo.service

import com.embea.insurancedemo.domain.InsuredPerson
import com.embea.insurancedemo.domain.Policy
import com.embea.insurancedemo.entity.InsuredPersonEntity
import com.embea.insurancedemo.entity.PolicyEntity
import com.embea.insurancedemo.repository.PolicyRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class PolicyService(
    private val policyRepository: PolicyRepository
) {

    fun savePolicy(policy: Policy): Policy {

        val policyEntity = PolicyMapper.toEntity(policy)
        return PolicyMapper.fromEntity(policyRepository.save(policyEntity))
    }

    fun updatePolicy(policy: Policy): Policy? {

        if (policyRepository.existsById(policy.policyId)) {
            policyRepository.deleteById(policy.policyId)
            val policyEntity = PolicyMapper.toEntity(policy)
            return PolicyMapper.fromEntity(policyRepository.save(policyEntity))
        }

        return null
    }

    fun getPolicy(policyId: UUID, requestDate: LocalDate): Policy? {

        return policyRepository.findByIdAndStartDateBefore(policyId, requestDate)?.let { PolicyMapper.fromEntity(it) }
    }
}

object PolicyMapper {

    fun toEntity(policy: Policy) = PolicyEntity(
        id = policy.policyId,
        startDate = policy.startDate,
        insuredPersons = policy.insuredPersons.map {
            InsuredPersonEntity(
                firstName = it.firstName,
                secondName = it.secondName,
                premium = it.premium
            )
        }
    )

    fun fromEntity(entity: PolicyEntity) = Policy(
        policyId = entity.id,
        startDate = entity.startDate,
        insuredPersons = entity.insuredPersons.map {
            InsuredPerson(
                id = it.id,
                firstName = it.firstName,
                secondName = it.secondName,
                premium = it.premium
            )
        }
    )
}
