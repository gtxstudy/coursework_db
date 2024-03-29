package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.FieldOfStudy
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.FieldOfStudyRepository
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class SetFieldOfStudyHandler(
    private val fieldOfStudyRepo: FieldOfStudyRepository,
    profileRepo: ProfileRepository,
    userRepo: UserRepository
) : ChangeProfileHandler(profileRepo, userRepo) {
    override fun mutateProfile(message: Message, user: User, profile: Profile) = profile.also {
        val fos = fieldOfStudyRepo.findById(profile.fieldOfStudyId!!).get()
        fieldOfStudyRepo.save(fos.copy(name=message.text))
    }

    override fun successMessage(bot: Bot, chatId: Long) =
        bot.sendMessage(
            ChatId.fromId(chatId),
            "На каком курсе вы обучаетесь"
        )

    override fun nextState() = UserStateEnum.SET_STUDY_YEAR

    override fun targetState() = UserStateEnum.SET_FIELD_OF_STUDY
}