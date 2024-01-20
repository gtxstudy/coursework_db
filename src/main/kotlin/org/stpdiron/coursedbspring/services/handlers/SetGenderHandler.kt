package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.SexEnum
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.CityRepository
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class SetGenderHandler(
    profileRepo: ProfileRepository,
    userRepo: UserRepository,
    private val cityRepo: CityRepository
) : ChangeProfileHandler(profileRepo, userRepo) {
    override fun mutateProfile(message: Message, user: User, profile: Profile) =
        when (message.text) {
            "Мужчина" -> profile.copy(sex = SexEnum.M)
            "Женщина" -> profile.copy(sex = SexEnum.F)
            else -> null
        }

    override fun successMessage(bot: Bot, chatId: Long): TelegramBotResult<out Any> {
        val buttons = cityRepo.findAll().map { city ->
            listOf(
                InlineKeyboardButton.CallbackData(
                text = city.name,
                callbackData = "city:${city.name}")
            )
        }
        return bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Выберите ваш город ",
            replyMarkup = InlineKeyboardMarkup.create(buttons),
        )
    }

    override fun nextState() = UserStateEnum.SET_CITY

    override fun targetState() = UserStateEnum.SET_GENDER
}