package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.CityRepository
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UniversityRepository
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class SetCityHandler(
    private val cityRepo: CityRepository,
    private val universityRepo: UniversityRepository,
    profileRepo: ProfileRepository,
    userRepo: UserRepository
): ChangeProfileCallbackHandler(userRepo, profileRepo) {
    override val prefix = "city"
    override fun mutateProfile(data: String, user: User, profile: Profile): Profile? {
        val city = cityRepo.findCityByName(data)!!
        return profile.copy(city = city.id)
    }

    override fun successMessage(bot: Bot, profile: Profile, chatId: Long) : TelegramBotResult<out Any> {
        val buttons = universityRepo.findAllByCity(profile.city!!).map { university ->
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = university.name,
                    callbackData = "university:${university.name}")
            )
        }
        return bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Выберите ваш университет",
            replyMarkup = InlineKeyboardMarkup.create(buttons),
        )
    }

    override fun nextState() = UserStateEnum.SET_UNIVERSITY
    override fun targetState() = UserStateEnum.SET_CITY

}