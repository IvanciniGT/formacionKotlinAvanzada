package controller

import io.ktor.server.application.*

interface PersonaController {
    suspend fun nuevaPersona(call: ApplicationCall)
    suspend fun recuperarPersona(call: ApplicationCall)
    suspend fun recuperarTodasLasPersonas(call: ApplicationCall)
}
