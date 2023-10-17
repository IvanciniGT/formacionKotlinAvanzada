package controller

import arrow.core.*
import arrow.core.Either
import controller.dto.DatosNuevaPersonaDTO
import controller.error.PersonaControllerError
import controller.mapper.datosNuevaPersonaDTO2Persona
import controller.mapper.persona2DatosPersonaDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import model.Persona
import model.error.PersonaArgumentError
import model.error.RepositorioError
import service.PersonaServicio
import javax.inject.Inject

class PersonaControllerImpl
@Inject constructor( private val personaServicio: PersonaServicio)
    :PersonaController{

    override suspend fun nuevaPersona(call: ApplicationCall) {
        val persona: DatosNuevaPersonaDTO = call.receive<DatosNuevaPersonaDTO>()
        val datosValidados: EitherNel<PersonaArgumentError, Persona> = datosNuevaPersonaDTO2Persona(persona)
        if (datosValidados.isLeft()){
            val error = PersonaControllerError()
            error.errorEnArgumentos = datosValidados.leftOrNull()!!
            call.respond(HttpStatusCode.BadRequest, error)
        }else {
            val personaNueva: Either<RepositorioError, Persona> =
                personaServicio.nuevaPersona(datosValidados.getOrNull()!!)
            if (personaNueva.isLeft()) {
                val error = PersonaControllerError()
                error.errorEnRepositorio = personaNueva.leftOrNull()!!
                call.respond(HttpStatusCode.InternalServerError, error)
            } else {
                call.respond(HttpStatusCode.Created, personaNueva.getOrNull()!!)
            }
        }
    }

    override suspend fun recuperarPersona(call: ApplicationCall) {
        call.parameters["id"]?: call.respond(HttpStatusCode.BadRequest, "Falta el id")
        val id: Int = call.parameters["id"]!!.toInt()
        personaServicio.recuperarPersona(id)
            .mapLeft {
                call.respond(HttpStatusCode.InternalServerError, PersonaControllerError(errorEnRepositorio = it))
            }
            .onRight {
                it?.let { persona -> call.respond(HttpStatusCode.OK,persona2DatosPersonaDTO(persona)) } ?:
                call.respond(HttpStatusCode.NotFound, "Persona no encontrada") }
    }

    override suspend fun recuperarTodasLasPersonas(call: ApplicationCall){
        val listado :Either<RepositorioError, List<Persona>> = personaServicio.recuperarTodasLasPersonas()
        listado.fold({
            val error = PersonaControllerError()
            error.errorEnRepositorio = it
            call.respond(HttpStatusCode.InternalServerError, error)
        }){
            val transformado = it.map {persona -> persona2DatosPersonaDTO(persona)}
            call.respond(HttpStatusCode.OK, transformado)
        }

    }

}