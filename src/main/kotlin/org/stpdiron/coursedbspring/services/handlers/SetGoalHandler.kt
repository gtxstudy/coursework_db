package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.GoalEnum
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class SetGoalHandler (
        userRepo: UserRepository,
        profileRepo: ProfileRepository
): ChangeProfileCallbackHandler(userRepo, profileRepo) {
    override val prefix = "goal"

    override fun nextState() = UserStateEnum.NULL

    override fun targetState() = UserStateEnum.SET_GOAL

    override fun mutateProfile(data: String, user: User, profile: Profile) = profile.copy(goal = GoalEnum.valueOf(data))

    override fun successMessage(bot: Bot, profile: Profile, chatId: Long): TelegramBotResult<out Any> {
        return bot.sendMessage(
            ChatId.fromId(chatId),
            "Настройка профиля закончена"
        )
    }
}