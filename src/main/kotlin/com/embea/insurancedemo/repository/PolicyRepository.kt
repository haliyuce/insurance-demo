package com.embea.insurancedemo.repository

import com.embea.insurancedemo.entity.PolicyEntity
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.util.*

interface PolicyRepository: CrudRepository<PolicyEntity, UUID> {

    fun findByIdAndStartDateBefore(id: UUID, date: LocalDate): PolicyEntity?
    fun deleteById(id: UUID?): PolicyEntity?
}
