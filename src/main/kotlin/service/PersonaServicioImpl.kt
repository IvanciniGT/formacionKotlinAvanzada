package service

import arrow.core.Either
import model.Persona
import model.error.RepositorioError
import repository.PersonaRepository
import javax.inject.Inject

// En nuestro proyecto, no hay lógica adicional al hecho de la persistencia.
// No mandamos correos, ni nada
class PersonaServicioImpl
    @Inject constructor (private val personaRepository: PersonaRepository) // Inyección de dependencias
    : PersonaServicio {

    override fun nuevaPersona(persona: Persona): Either<RepositorioError, Persona> = personaRepository.guardarPersona(persona)

    override fun recuperarPersona(id: Int): Either<RepositorioError, Persona?>  = personaRepository.recuperarPersona(id)

    override fun recuperarTodasLasPersonas(): Either<RepositorioError, List<Persona>> = personaRepository.recuperarTodasLasPersonas()
}