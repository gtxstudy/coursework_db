package org.stpdiron.coursedbspring

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

@Table("service_user")
class User(
    @Id
    val id: Long?,
    val created: LocalDateTime,
    val active: Boolean,
    val reactionsFrom: Long,
    val reactionsTo: Long
)