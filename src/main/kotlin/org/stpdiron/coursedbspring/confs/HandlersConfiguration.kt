package org.stpdiron.coursedbspring.confs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.services.handlers.SetAgeHandler
import org.stpdiron.coursedbspring.services.handlers.SetNameHandler
import org.stpdiron.coursedbspring.services.handlers.UserStateHandler

@Configuration
class HandlersConfiguration(
    private val nameHandler: SetNameHandler,
    private val ageHandler: SetAgeHandler,
) {
    @Bean
    fun handlersMap() : Map<UserStateEnum, UserStateHandler> = mapOf(
        Pair(nameHandler.targetState(), nameHandler),
        Pair(ageHandler.targetState(), ageHandler),
    )
}