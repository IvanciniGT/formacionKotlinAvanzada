package service

import arrow.core.right
import model.Persona
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.kotlin.whenever
import repository.PersonaRepository
import kotlin.test.Test

class PersonaControllerTest {

    private val personaRepositorio: PersonaRepository = Mockito.mock( // Esto es magia de Mockito
        PersonaRepository::class.java )
    // Esto es magia de Mockito
    // Esto crea en automático una clase Kotlin que implementa PersonaRepository
    // Devolviendo por defecto los valores más básicos posibles (Dummy: null, 0, false, listas vacias)
    // Esto no vamos a re implementar... pero... solo lo que necesito para probar
    private val personaController: PersonaController = PersonaControllerImpl(personaRepositorio)

    @Test
    fun `Recuperar una persona por su id`(){
        // Esta es la magia de Mockito
        doReturn(
            Persona(
                id = 1,
                nombre = PersonaDatos.nombreOK,
                email = PersonaDatos.emailOK,
                edad = PersonaDatos.edadOK,
                dni = PersonaDatos.dniOK
            ).getOrNull().right()
        ).whenever(personaRepositorio).recuperarPersona(1)

        val persona = personaController.recuperarPersona(1)
        assertTrue(persona.isRight())
        // Comprobar que se devuelve lo que debe devolverse... Lo que hay en el repo
        assertEquals(PersonaDatos.nombreOK, persona.getOrNull()!!.nombre)
    }

    @Test
    fun `Recuperar todas las personas`(){
        // Esta es la magia de Mockito
        doReturn(
            listOf(
                Persona(
                    id = 1,
                    nombre = PersonaDatos.nombreOK,
                    email = PersonaDatos.emailOK,
                    edad = PersonaDatos.edadOK,
                    dni = PersonaDatos.dniOK
                ).getOrNull()!!,
                Persona(
                    id = 2,
                    nombre = "Maria",
                    email = "lerez@maria.com",
                    edad = 30,
                    dni = "12345678A"
                ).getOrNull()!!
            ).right()
        ).whenever(personaRepositorio).recuperarTodasLasPersonas()

        val listadoPersonas = personaController.recuperarTodasLasPersonas()
        assertTrue(listadoPersonas.isRight())
        // Comprobar que se devuelve lo que debe devolverse... Lo que hay en el repo
        assertTrue(listadoPersonas.getOrNull()!!.size == 2)
        assertEquals(listadoPersonas.getOrNull()!![0].nombre, PersonaDatos.nombreOK)
        assertEquals(listadoPersonas.getOrNull()!![1].nombre, "Maria")

    }

}