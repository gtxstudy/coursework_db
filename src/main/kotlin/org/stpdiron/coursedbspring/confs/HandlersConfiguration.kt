package org.stpdiron.coursedbspring.confs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.stpdiron.coursedbspring.CommandEnum
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.services.handlers.SetYearOfStudyHandler
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
        private val imagesHandler: SetImagesHandler,
        private val descriptionHandler: SetDescriptionHandler,
        private val goalHandler: SetGoalHandler,

        private val browsingCallbackHandler: BrowsingCallbackHandler,
        private val browsingIncomingCallbackHandler: BrowsingIncomingCallbackHandler,

        private val changeProfileCommandHandler: ChangeProfileCommandHandler,
        private val showProfileCommandHandler: ShowProfileCommandHandler,
        private val browseCommandHandler: BrowseCommandHandler,
        private val browseIncomingCommandHandler: BrowseIncomingCommandHandler,
) {
    @Bean
    fun messageHandlersMap() : Map<UserStateEnum, UserMessageHandler> = mapOf(
        Pair(nameHandler.targetState(), nameHandler),
        Pair(ageHandler.targetState(), ageHandler),
        Pair(genderHandler.targetState(), genderHandler),
        Pair(fieldOfStudyHandler.targetState(), fieldOfStudyHandler),
        Pair(yearOfStudyHandler.targetState(), yearOfStudyHandler),
        Pair(imagesHandler.targetState(), imagesHandler),
        Pair(descriptionHandler.targetState(), descriptionHandler),
    )

    @Bean
    fun callbackHandlersMap(): Map<UserStateEnum, UserCallbackHandler> = mapOf(
        Pair(cityHandler.targetState(), cityHandler),
        Pair(universityHandler.targetState(), universityHandler),
        Pair(goalHandler.targetState(), goalHandler),
        Pair(browsingCallbackHandler.targetState(), browsingCallbackHandler),
        Pair(browsingIncomingCallbackHandler.targetState(), browsingIncomingCallbackHandler),
    )

    @Bean
    fun commandHandlersMap(): Map<CommandEnum, CommandHandler> = mapOf(
        Pair(changeProfileCommandHandler.commandType(), changeProfileCommandHandler),
        Pair(showProfileCommandHandler.commandType(), showProfileCommandHandler),
        Pair(browseCommandHandler.commandType(), browseCommandHandler),
        Pair(browseIncomingCommandHandler.commandType(), browseIncomingCommandHandler),
    )
}