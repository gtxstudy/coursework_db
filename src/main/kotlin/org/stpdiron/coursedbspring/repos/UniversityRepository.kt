package org.stpdiron.coursedbspring.repos

import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.University

interface UniversityRepository: CrudRepository<University, Long>
