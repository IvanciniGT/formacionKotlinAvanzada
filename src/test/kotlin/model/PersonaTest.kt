package model

import arrow.core.EitherNel
import arrow.core.left
import model.error.PersonaArgumentError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersonaTest {

    @Test
    fun `Una persona con datos correctos debería poder crearse sin problema`(){
        val persona: EitherNel<PersonaArgumentError, Persona> = Persona(PersonaDatos.nombreOK, PersonaDatos.edadOK,PersonaDatos.emailOK,PersonaDatos. dniOK)
        assertTrue(persona.isRight())
        assertEquals(PersonaDatos.nombreOK, persona.getOrNull()?.nombre)
        assertEquals(PersonaDatos.edadOK, persona.getOrNull()?.edad)
        assertEquals(PersonaDatos.emailOK, persona.getOrNull()?.email)
        assertEquals(PersonaDatos.dniOK, persona.getOrNull()?.dni)
    }
    @Test
    fun `Una persona con datos incorrectos NO debería poder crearse (Debe devolver errores)`(){
        val persona: EitherNel<PersonaArgumentError, Persona> = Persona(PersonaDatos.nombreNOK, PersonaDatos.edadNOK, PersonaDatos.emailNOK, PersonaDatos.dniNOK)
        assertTrue(persona.isLeft())
        assertEquals(4, persona.leftOrNull()?.size)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalNombre)!!)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalEdad)!!)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalEmail)!!)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalDni)!!)
    }
    @Test
    fun `Una persona con email incorrecto NO debería poder crearse (Debe devolver un error para el email)`(){
        val persona: EitherNel<PersonaArgumentError, Persona> = Persona(PersonaDatos.nombreOK, PersonaDatos.edadOK, PersonaDatos.emailNOK, PersonaDatos.dniOK)
        assertTrue(persona.isLeft())
        assertEquals(1, persona.leftOrNull()?.size)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalEmail)!!)
    }

}