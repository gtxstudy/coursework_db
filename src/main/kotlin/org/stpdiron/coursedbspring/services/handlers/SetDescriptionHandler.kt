package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.FieldOfStudyRepository
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class SetDescriptionHandler(
    profileRepo: ProfileRepository,
    userRepo: UserRepository
) : ChangeProfileHandler(profileRepo, userRepo) {
    override fun mutateProfile(message: String, user: User, profile: Profile) =
        profile.copy(about = message)

    override fun successMessage(bot: Bot, chatId: Long) =
        bot.sendMessage(
            ChatId.fromId(chatId),
            "Настройка профиля закончена"
        )

    override fun nextState() = UserStateEnum.BROWSING

    override fun targetState() = UserStateEnum.SET_DESCRIPTION
}