package org.stpdiron.coursedbspring

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.time.LocalDateTime

enum class CommandEnum {
    START, CHANGE_PROFILE, SHOW_PROFILE, BROWSE,
}

enum class UserStateEnum {
    NULL, SET_NAME, SET_AGE, SET_GENDER, SET_GOAL, SET_CITY, SET_UNIVERSITY,
    SET_FACULTY, SET_DESCRIPTION, SET_FIELD_OF_STUDY, SET_IMAGES, BROWSING,
    SET_STUDY_YEAR
}
@Table("service_user")
data class User(
    @Id
    val id: Long?,
    val userId: Long,
    var state: UserStateEnum,
    val created: LocalDateTime,
    val active: Boolean,
    val reactionsFrom: Long,
    val reactionsTo: Long,
)

enum class ReactionType { LIKE, SKIP }
data class Reaction(
    @Id
    val id: Long?,
    val fromId: Long,
    val toId: Long,
    val seen: Boolean,
    val type: ReactionType,
    val at: LocalDateTime,
)

enum class SexEnum { M, F, OTHER }
enum class GoalEnum { WORK, STUDY, SCIENCE, RELATIONSHIP, FRIENDSHIP }
data class Profile(
    @Id
    val id: Long?,
    val userId: Long,
    val name: String?,
    val age: Long?,
    val sex: SexEnum?,
    val fieldOfStudyId: Long?,
    val country: String?,
    val city: Long?,
    val about: String?,
    val goal: GoalEnum?,
    val modified: LocalDateTime,
) {
    fun getDescription(city: City?, fieldOfStudy: FieldOfStudy?, university: University?) =
        "$name, $age, ${city?.name}\n${university?.name}, ${fieldOfStudy?.name}, ${fieldOfStudy?.year}\n\n$about"
}

data class City(
    @Id
    val id: Long?,
    val name: String
)

data class Image(
    @Id
    val id: Long?,
    val profileId: Long,
    val tgId: String,
    val uploaded: LocalDateTime,
)

data class University(
    @Id
    val id: Long?,
    val name: String,
    val city: Long,
)

data class Faculty(
    @Id
    val id: Long?,
    val universityId: Long,
    val name: String,
)

data class FieldOfStudy(
    @Id
    val id: Long?,
    val universityId: Long,
    val name: String?,
    val year: Long?,
)