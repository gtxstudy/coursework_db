package org.stpdiron.coursedbspring.repos

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.Reaction
import org.stpdiron.coursedbspring.ReactionType
import org.stpdiron.coursedbspring.UserStateEnum

@WritingConverter
class ReactionWriteConverter: Converter<ReactionType, String> {
    val logger = KotlinLogging.logger {  }
    override fun convert(source: ReactionType): String {
        logger.warn { "CONVERT" }
        return source.toString()
    }
}

@ReadingConverter
class ReactionReadConverter: Converter<String, ReactionType> {
    override fun convert(source: String) = ReactionType.valueOf(source)
}

interface ReactionRepository: CrudRepository<Reaction, Long> {
    fun findByToId(toId: Long): List<Reaction>

    fun findByFromId(fromId: Long): List<Reaction>
}