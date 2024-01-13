package org.stpdiron.coursedbspring.repos

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.GoalEnum
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.ReactionType
import org.stpdiron.coursedbspring.SexEnum

@WritingConverter
class SexWriteConverter: Converter<SexEnum, String> {
    override fun convert(source: SexEnum) = source.toString()
}
@ReadingConverter
class SexReadConverter: Converter<String, SexEnum> {
    override fun convert(source: String) = SexEnum.valueOf(source)
}

@WritingConverter
class GoalWriteConverter: Converter<GoalEnum, String> {
    override fun convert(source: GoalEnum) = source.toString()
}
@ReadingConverter
class GoalReadConverter: Converter<String, GoalEnum> {
    override fun convert(source: String) = GoalEnum.valueOf(source)
}

interface ProfileRepository: CrudRepository<Profile, Long> {
    fun findByUserId(userId: Long): Profile
}