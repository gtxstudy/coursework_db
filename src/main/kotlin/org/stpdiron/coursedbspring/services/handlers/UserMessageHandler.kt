package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum

interface UserMessageHandler {
    fun handle(user: User, bot: Bot, message: Message): TelegramBotResult<out Any>
    fun targetState() : UserStateEnum
}