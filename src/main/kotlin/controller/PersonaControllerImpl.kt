package service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import controller.PersonaController
import controller.dto.DatosNuevaPersonaDTO
import controller.dto.DatosPersonaDTO
import controller.error.PersonaControllerError
import controller.mapper.persona2DatosPersonaDTO
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
        // Escribir
    }

    override fun recuperarPersona(id: Int): Either<PersonaControllerError, DatosPersonaDTO?> {
        TODO("Not yet implemented")
        // Escribir
    }

    override fun recuperarTodasLasPersonas(): Either<PersonaControllerError, List<DatosPersonaDTO>> {
        val listado :Either<RepositorioError, List<Persona>> = personaServicio.recuperarTodasLasPersonas()
        listado.fold({
            val error = PersonaControllerError()
            error.errorEnRepositorio = it
            return error.left()
        }){
            return it.map {persona2DatosPersonaDTO(it)}.right()
        }
        // Escribir esto con map y flatMap
    }

}