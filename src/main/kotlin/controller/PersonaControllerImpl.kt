package service

import arrow.core.Either
import controller.PersonaController
import controller.dto.DatosNuevaPersonaDTO
import controller.dto.DatosPersonaDTO
import controller.error.PersonaControllerError
import model.Persona
import model.error.RepositorioError
import repository.PersonaRepository


class PersonaControllerImpl
constructor (private val personaServicio: PersonaServicio)
    :PersonaController{

    override fun nuevaPersona(persona: DatosNuevaPersonaDTO): Either<PersonaControllerError, DatosPersonaDTO> {
        //val persona: Persona =  // Al hacer la conversión de datos... de DatosNuevaPersonaDTO -> Persona
        // Me puede dar un error en las validaciones de los datos
        //personaServicio.nuevaPersona(persona)
        TODO("Not yet implemented")
    }

    override fun recuperarPersona(id: Int): Either<PersonaControllerError, DatosPersonaDTO?> {
        TODO("Not yet implemented")
    }

    override fun recuperarTodasLasPersonas(): Either<PersonaControllerError, List<DatosPersonaDTO>> {
        TODO("Not yet implemented")
    }

}