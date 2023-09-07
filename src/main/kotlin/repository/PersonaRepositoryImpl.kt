package repository

import arrow.atomic.AtomicInt
import arrow.core.Either
import arrow.core.right
import model.Persona
import model.error.RepositorioError
import java.util.*

class PersonaRepositoryImpl : PersonaRepository {

    private val listadoPersonas = mutableListOf<Persona>()
    private val nextId = AtomicInt(0)
    override fun guardarPersona(persona: Persona): Either<RepositorioError, Persona> {
        if(persona.id == null)
            persona.id = nextId.getAndIncrement()
        listadoPersonas.find {it.id == persona.id }?.let {
            listadoPersonas.remove(it)
        }
        listadoPersonas.add(persona)
        return persona.right()
    }

    override fun recuperarPersona(id: Int): Either<RepositorioError, Persona?> = listadoPersonas.find {it.id == id }.right()

    override fun recuperarTodasLasPersonas(): Either<RepositorioError, List<Persona>> {
        return Collections.unmodifiableCollection(listadoPersonas).toList().right()
    }
}