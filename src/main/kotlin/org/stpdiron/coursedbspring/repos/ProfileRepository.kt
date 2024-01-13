package org.stpdiron.coursedbspring.repos

import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.Profile

interface ProfileRepository: CrudRepository<Profile, Long> {
    fun findByUserId(userId: Long): Profile
}