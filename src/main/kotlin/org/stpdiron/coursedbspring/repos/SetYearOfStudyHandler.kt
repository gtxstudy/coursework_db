package org.stpdiron.coursedbspring.repos

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.FieldOfStudy
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.services.handlers.ChangeProfileCallbackHandler
import org.stpdiron.coursedbspring.services.handlers.ChangeProfileHandler

@Component
class SetYearOfStudyHandler(
    private val fieldOfStudyRepo: FieldOfStudyRepository,
    profileRepo: ProfileRepository,
    userRepo: UserRepository
) : ChangeProfileHandler(profileRepo, userRepo) {
    override fun mutateProfile(message: String, user: User, profile: Profile) = message.toLongOrNull()?.let {
        val fos = fieldOfStudyRepo.findById(profile.fieldOfStudyId!!).get()
        fieldOfStudyRepo.save(fos.copy(year = it))
        profile
    }

    override fun successMessage(bot: Bot, chatId: Long) =
        bot.sendMessage(
            ChatId.fromId(chatId),
            "Введите описание для профиля"
        )

    override fun nextState() = UserStateEnum.SET_DESCRIPTION

    override fun targetState() = UserStateEnum.SET_STUDY_YEAR
}