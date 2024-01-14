package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class SetAgeHandler(profileRepo: ProfileRepository, userRepo: UserRepository) :
    ChangeProfileHandler(profileRepo, userRepo) {

    private val keyboard = listOf(
        listOf(KeyboardButton("Мужчина")),
        listOf(KeyboardButton("Женщина"))
    )
    override fun mutateProfile(message: String, user: User, profile: Profile) : Profile? =
        message.toLongOrNull()?.let {
            profile.copy(age = it)
        }

    override fun successMessage(bot: Bot, chatId: Long): TelegramBotResult<out Any> {
        val keyboardMarkup = KeyboardReplyMarkup(keyboard = keyboard, resizeKeyboard = true)
        return bot.sendMessage(
            ChatId.fromId(chatId),
            "Укажите ваш пол",
            replyMarkup = keyboardMarkup
        )
    }

    override fun targetState() = UserStateEnum.SET_AGE
    override fun nextState() = UserStateEnum.SET_GENDER
}