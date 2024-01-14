package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.*
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository
import java.time.LocalDateTime

@Component
class SetNameHandler(profileRepo: ProfileRepository, userRepo: UserRepository) :
    ChangeProfileHandler(profileRepo, userRepo) {

    override fun mutateProfile(message: String, user: User, profile: Profile): Profile?
        = profile.copy(name = message)

    override fun successMessage(bot: Bot, chatId: Long) = bot.sendMessage(
        ChatId.fromId(chatId),
        "Введите ваш возраст"
    )

    override fun targetState() = UserStateEnum.SET_NAME
    override fun nextState() = UserStateEnum.SET_AGE
}