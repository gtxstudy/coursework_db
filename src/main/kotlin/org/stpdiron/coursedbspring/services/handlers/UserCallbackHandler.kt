package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum

interface UserCallbackHandler {
    fun handle(user: User, bot: Bot, callback: CallbackQuery): TelegramBotResult<out Any>
    fun targetState() : UserStateEnum
}