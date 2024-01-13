package org.stpdiron.coursedbspring.repos

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum

@WritingConverter
class UserStateEnumToStringConverter: Converter<UserStateEnum, String> {
    override fun convert(source: UserStateEnum): String {
        print(source.toString())
        return source.toString()
    }
}

interface UserRepository: CrudRepository<User, Long> {
    fun findByUserId(userId: Long): User?
}