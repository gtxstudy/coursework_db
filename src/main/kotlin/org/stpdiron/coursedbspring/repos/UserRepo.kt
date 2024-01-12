package org.stpdiron.coursedbspring.repos

import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.User

interface UserRepo: CrudRepository<User, Long>