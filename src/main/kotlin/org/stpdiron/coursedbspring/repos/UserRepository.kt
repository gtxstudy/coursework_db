package org.stpdiron.coursedbspring.repos

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum

@WritingConverter
class UserStateWriteConverter: Converter<UserStateEnum, String> {
    val logger = KotlinLogging.logger {  }
    override fun convert(source: UserStateEnum): String {
        logger.warn { "CONVERT" }
        return source.toString()
    }
}

@ReadingConverter
class UserStateReadConverter: Converter<String, UserStateEnum> {
    override fun convert(source: String) = UserStateEnum.valueOf(source)
}

interface UserRepository: CrudRepository<User, Long> {
    fun findByUserId(userId: Long): User?
}