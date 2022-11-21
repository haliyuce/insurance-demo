package com.embea.insurancedemo.controller

import com.embea.insurancedemo.controller.mapper.PolicyMapper
import com.embea.insurancedemo.dto.*
import com.embea.insurancedemo.service.PolicyService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("/policies")
class PolicyController(
    private val policyService: PolicyService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody createPolicyRequest: CreatePolicyRequest): CreatePolicyResponse {

        return PolicyMapper.mapCreatePolicyResponseFromPolicy(
            policyService.savePolicy(PolicyMapper.createPolicyFromCreatePolicyRequest(createPolicyRequest))
        )
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@Valid @RequestBody updatePolicyRequest: UpdatePolicyRequest): UpdatePolicyResponse {

        return PolicyMapper.mapUpdatePolicyResponseFromPolicy(
            policyService.updatePolicy(PolicyMapper.createPolicyFromUpdateDto(updatePolicyRequest))
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Policy not found!")
        )
    }

    @GetMapping
    fun get(@RequestBody getPolicyRequest: GetPolicyRequest): GetPolicyResponse {

        val policy = policyService.getPolicy(getPolicyRequest.policyId, getPolicyRequest.requestDate)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Policy not found!")

        return PolicyMapper.mapGetPolicyResponseFromPolicy(
            policy, getPolicyRequest.requestDate
        )
    }
}
