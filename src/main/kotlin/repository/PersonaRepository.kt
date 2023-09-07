package repository

import arrow.core.Either
import model.Persona
import model.error.RepositorioError

interface PersonaRepository {
    fun guardarPersona(persona: Persona): Either<RepositorioError,Persona>
    fun recuperarPersona(id: Int): Either<RepositorioError,Persona?>
    fun recuperarTodasLasPersonas(): Either<RepositorioError,List<Persona>>
}