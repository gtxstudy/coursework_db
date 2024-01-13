package org.stpdiron.coursedbspring

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.time.LocalDateTime

enum class UserStateEnum { NEW, SET_ACTIVE, SET_NAME, SET_AGE, SET_SEX, SET_COUNTRY, SET_CITY, SET_GOAL, SET_ABOUT, SET_UNIVERSITY, SET_FACULTY, SET_FIELD_OF_STUDY, SET_IMAGES, VIEW_PROFILES, VIEW_LIKED_PROFILES }
@Table("service_user")
class User(
    @Id
    val id: Long?,
    val userId: Long,
    var state: UserStateEnum,
    val created: LocalDateTime,
    var active: Boolean,
    val reactionsFrom: Long,
    val reactionsTo: Long,
)

enum class ReactionType { LIKE, SKIP }
class Reaction(
    @Id
    val id: Long?,
    val fromId: Long,
    val toId: Long,
    val type: ReactionType,
    val at: LocalDateTime,
)

enum class SexEnum { M, F, OTHER }
enum class GoalEnum { WORK, STUDY, SCIENCE, RELATIONSHIP, FRIENDSHIP }
class Profile(
    @Id
    val id: Long?,
    val userId: Long,
    val name: String,
    val age: Long?,
    val sex: SexEnum?,
    val fieldOfStudyId: Long?,
    val country: String?,
    val city: String?,
    val about: String?,
    val goal: GoalEnum,
    val modified: LocalDateTime,
)

class Image(
    @Id
    val id: Long?,
    val profileId: Long,
    val path: String,
    val uploaded: LocalDateTime,
)

class University(
    @Id
    val id: Long?,
    val name: String,
    val country: String?,
    val city: String?,
)

class Faculty(
    @Id
    val id: Long?,
    val universityId: Long,
    val name: String,
)

class FieldOfStudy(
    @Id
    val id: Long?,
    val facultyId: Long,
    val name: String,
    val year: Long,
)