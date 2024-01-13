package org.stpdiron.coursedbspring.repos

import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.Faculty

interface FacultyRepository: CrudRepository<Faculty, Long> {
    fun findByUniversityId(universityId: Long): List<Faculty>
}