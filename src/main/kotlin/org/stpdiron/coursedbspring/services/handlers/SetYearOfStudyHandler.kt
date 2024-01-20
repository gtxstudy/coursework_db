package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.FieldOfStudyRepository
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class SetYearOfStudyHandler(
        private val fieldOfStudyRepo: FieldOfStudyRepository,
        profileRepo: ProfileRepository,
        userRepo: UserRepository
) : ChangeProfileHandler(profileRepo, userRepo) {
    override fun mutateProfile(message: Message, user: User, profile: Profile) = message.text?.toLongOrNull()?.let {
        val fos = fieldOfStudyRepo.findById(profile.fieldOfStudyId!!).get()
        fieldOfStudyRepo.save(fos.copy(year = it))
        profile
    }

    override fun successMessage(bot: Bot, chatId: Long) =
        bot.sendMessage(
            ChatId.fromId(chatId),
            "Отправьте фотографию, которую хотите прикрепить к профилю"
        )

    override fun nextState() = UserStateEnum.SET_IMAGES

    override fun targetState() = UserStateEnum.SET_STUDY_YEAR
}