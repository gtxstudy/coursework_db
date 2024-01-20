package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.GoalEnum
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
    override fun mutateProfile(message: Message, user: User, profile: Profile) =
        profile.copy(about = message.text)

    override fun successMessage(bot: Bot, chatId: Long): TelegramBotResult<Message> {
        val buttons = GoalEnum.entries.map { goal ->
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = goal.toString(),
                    callbackData = "goal:${goal}")
            )
        }
        return bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Что хотите найти?",
            replyMarkup = InlineKeyboardMarkup.create(buttons),
        )
    }

    override fun nextState() = UserStateEnum.SET_GOAL

    override fun targetState() = UserStateEnum.SET_DESCRIPTION
}