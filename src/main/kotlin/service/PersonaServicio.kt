package service

import arrow.core.Either
import model.Persona
import model.error.RepositorioError

interface PersonaServicio {
    fun nuevaPersona(persona: Persona): Either<RepositorioError, Persona>
    //fun modificarPersona(persona: Persona): Either<RepositorioError, Persona>
    fun recuperarPersona(id: Int): Either<RepositorioError, Persona?>
    fun recuperarTodasLasPersonas(): Either<RepositorioError, List<Persona>>
}