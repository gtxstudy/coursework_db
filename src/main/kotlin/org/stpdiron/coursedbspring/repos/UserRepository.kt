package org.stpdiron.coursedbspring.repos

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.repository.query.Query
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

    @Query("select u2.* from service_user as u1 " +
            "join profile as p1 on p1.user_id = u1.id " +
            "join city as c1 on p1.city = c1.id " +
            "join profile as p2 on p2.goal = p1.goal " +
            "join city as c2 on p2.city = c2.id " +
            "join service_user as u2 on p2.user_id = u2.id " +
            "left join reaction as r on (r.from_id = u1.id and r.to_id = u2.id) or (r.from_id = u2.id and r.to_id = u1.id) " +
            "where u1.id = :userId and u2.id != :userId and r.id is null and c1.name = c2.name " +
            "limit 1"
    )
    fun recommendUserById(userId: Long): User?

    @Query("select u2.* from service_user as u1 " +
            "join reaction as r on r.to_id = u1.id " +
            "join service_user as u2 on r.from_id = u2.id " +
            "where u1.id = :userId and r.seen = false " +
            "limit 1"
    )
    fun incomingUserById(userId: Long): User?
}