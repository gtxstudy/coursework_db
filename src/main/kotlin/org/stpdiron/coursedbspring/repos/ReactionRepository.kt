package org.stpdiron.coursedbspring.repos

import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.Reaction

interface ReactionRepository: CrudRepository<Reaction, Long> {
    fun findByToId(toId: Long): List<Reaction>

    fun findByFromId(fromId: Long): List<Reaction>
}