package repository

import arrow.core.Either
import arrow.core.EitherNel
import model.Persona
import model.error.PersonaArgumentError
import model.error.RepositorioError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class PersonaRepositoryTest {

    private val repository = PersonaRepositoryImpl()

    @Test
    fun `Recuperar un usuario que no existe` () {
        assertNull(repository.recuperarPersona(-1).getOrNull())
    }

    @Test
    fun `Probar a dar de alta un usuario sin id` (){
        val persona: EitherNel<PersonaArgumentError, Persona> = Persona(PersonaDatos.nombreOK, PersonaDatos.edadOK, PersonaDatos.emailOK, PersonaDatos.dniOK)
        persona.onRight {
            val personaGuardada: Either<RepositorioError, Persona> = repository.guardarPersona(it)
            assertTrue(personaGuardada.isRight())
            assertEquals(it.nombre, PersonaDatos.nombreOK)
            assertEquals(it.edad, PersonaDatos.edadOK)
            assertEquals(it.email, PersonaDatos.emailOK)
            assertEquals(it.dni, PersonaDatos.dniOK)
            assertNotNull(it.id)
            assertTrue(it.id!! >= 0)
        }
    }

    @Test
    fun `Recuperar una persona que se que existe (por su id)` (){
        val persona: EitherNel<PersonaArgumentError, Persona> = Persona(PersonaDatos.nombreOK, PersonaDatos.edadOK, PersonaDatos.emailOK, PersonaDatos.dniOK)
        persona.onRight {
            val personaGuardada: Either<RepositorioError, Persona> = repository.guardarPersona(it)
            assertTrue(personaGuardada.isRight())
            val personaRecuperada: Either<RepositorioError, Persona?> = repository.recuperarPersona(it.id!!)
            assertTrue(personaRecuperada.isRight())
            assertEquals(it.nombre, personaRecuperada.getOrNull()?.nombre)
            assertEquals(it.edad, personaRecuperada.getOrNull()?.edad)
            assertEquals(it.email, personaRecuperada.getOrNull()?.email)
            assertEquals(it.dni, personaRecuperada.getOrNull()?.dni)
            assertEquals(it.id, personaRecuperada.getOrNull()?.id)
        }
    }
}