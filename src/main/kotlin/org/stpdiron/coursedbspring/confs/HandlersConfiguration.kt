package org.stpdiron.coursedbspring.confs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.SetYearOfStudyHandler
import org.stpdiron.coursedbspring.services.handlers.*

@Configuration
class HandlersConfiguration(
    private val nameHandler: SetNameHandler,
    private val ageHandler: SetAgeHandler,
    private val genderHandler: SetGenderHandler,
    private val cityHandler: SetCityHandler,
    private val universityHandler: SetUniversityHandler,
    private val fieldOfStudyHandler: SetFieldOfStudyHandler,
    private val yearOfStudyHandler: SetYearOfStudyHandler,
    private val descriptionHandler: SetDescriptionHandler
) {
    @Bean
    fun messageHandlersMap() : Map<UserStateEnum, UserMessageHandler> = mapOf(
        Pair(nameHandler.targetState(), nameHandler),
        Pair(ageHandler.targetState(), ageHandler),
        Pair(genderHandler.targetState(), genderHandler),
        Pair(fieldOfStudyHandler.targetState(), fieldOfStudyHandler),
        Pair(yearOfStudyHandler.targetState(), yearOfStudyHandler),
        Pair(descriptionHandler.targetState(), descriptionHandler)
    )

    @Bean
    fun callbackHandlersMap(): Map<UserStateEnum, UserCallbackHandler> = mapOf(
        Pair(cityHandler.targetState(), cityHandler),
        Pair(universityHandler.targetState(), universityHandler)
    )
}