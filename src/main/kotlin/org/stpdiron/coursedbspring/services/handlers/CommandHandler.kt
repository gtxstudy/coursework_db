package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Message
import org.stpdiron.coursedbspring.CommandEnum
import org.stpdiron.coursedbspring.User

interface CommandHandler {
    fun execute(user: User, bot: Bot, message: Message)

    fun commandType(): CommandEnum
}