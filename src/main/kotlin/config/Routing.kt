package config

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


fun Application.configureRouting() {
                                                             // Arranco la inyección de dependencias
    val personaController =  DaggerPersonaControllerComponent.create().getPersonaController() // by inject()
                                                                      // Dame una instancia del PersonaController
    routing {
        route("api/v1/user") {
            post {
                personaController.nuevaPersona(call)
            }
            get("{id}") {
                personaController.recuperarPersona(call)
            }
            get() {
                personaController.recuperarTodasLasPersonas(call)
            }
        }
    }
}
