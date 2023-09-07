package controller

import arrow.core.Either
import controller.dto.DatosNuevaPersonaDTO
import controller.dto.DatosPersonaDTO
import controller.error.PersonaControllerError
import model.Persona
import model.error.RepositorioError
import service.PersonaServicio

interface PersonaController {
    fun nuevaPersona(persona: DatosNuevaPersonaDTO): Either<PersonaControllerError, DatosPersonaDTO>
    fun recuperarPersona(id: Int): Either<PersonaControllerError, DatosPersonaDTO?>
    fun recuperarTodasLasPersonas(): Either<PersonaControllerError, List<DatosPersonaDTO>>
}
