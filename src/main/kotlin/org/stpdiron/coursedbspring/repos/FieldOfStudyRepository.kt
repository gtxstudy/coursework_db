package org.stpdiron.coursedbspring.repos

import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.FieldOfStudy

interface FieldOfStudyRepository: CrudRepository<FieldOfStudy, Long> {

}