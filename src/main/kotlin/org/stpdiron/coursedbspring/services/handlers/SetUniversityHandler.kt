package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.FieldOfStudy
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.FieldOfStudyRepository
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UniversityRepository
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class SetUniversityHandler(
    val universityRepo: UniversityRepository,
    val fieldOfStudyRepo: FieldOfStudyRepository,
    userRepo: UserRepository,
    profileRepo: ProfileRepository
) : ChangeProfileCallbackHandler(userRepo, profileRepo) {
    override val prefix = "university"

    override fun mutateProfile(data: String, user: User, profile: Profile): Profile? {
        val university = universityRepo.findByName(data)!!
        val fieldOfStudy = FieldOfStudy(
            null,
            university.id!!,
            null,
            null
        )
        return profile.copy(fieldOfStudyId = fieldOfStudyRepo.save(fieldOfStudy).id)
    }

    override fun successMessage(bot: Bot, profile: Profile, chatId: Long) =
        bot.sendMessage(
            ChatId.fromId(chatId),
            "На каком направлении вы обучаетесь"
        )

    override fun nextState() = UserStateEnum.SET_FIELD_OF_STUDY
    override fun targetState() = UserStateEnum.SET_UNIVERSITY
}