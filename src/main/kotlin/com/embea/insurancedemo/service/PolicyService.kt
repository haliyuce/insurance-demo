package com.embea.insurancedemo.service

import com.embea.insurancedemo.domain.Policy
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import javax.transaction.Transactional

@Service
class PolicyService() {

    fun savePolicy(policy: Policy): Policy {

        TODO()
    }

    @Transactional
    fun updatePolicy(policy: Policy): Policy {

        TODO()
    }

    fun getPolicy(policyId: UUID, requestDate: LocalDate): Policy? {

        TODO()
    }
}
