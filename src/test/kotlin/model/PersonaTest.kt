package model

import arrow.core.EitherNel
import arrow.core.left
import model.error.PersonaArgumentError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersonaTest {

    val nombreOK = "Juan"
    val nombreNOK = "juan"

    val edadOK = 30
    val edadNOK = -1

    val emailOK = "juan@juan.com"
    val emailNOK = "https://ruina.com"

    val dniOK = "12345678A"
    val dniNOK = "12345678"
    @Test
    fun `Una persona con datos correctos debería poder crearse sin problema`(){
        val persona: EitherNel<PersonaArgumentError, Persona> = Persona(nombreOK, edadOK, emailOK, dniOK)
        assertTrue(persona.isRight())
        assertEquals(nombreOK, persona.getOrNull()?.nombre)
        assertEquals(edadOK, persona.getOrNull()?.edad)
        assertEquals(emailOK, persona.getOrNull()?.email)
        assertEquals(dniOK, persona.getOrNull()?.dni)
    }
    @Test
    fun `Una persona con datos incorrectos NO debería poder crearse (Debe devolver errores)`(){
        val persona: EitherNel<PersonaArgumentError, Persona> = Persona(nombreNOK, edadNOK, emailNOK, dniNOK)
        assertTrue(persona.isLeft())
        assertEquals(4, persona.leftOrNull()?.size)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalNombre)!!)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalEdad)!!)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalEmail)!!)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalDni)!!)
    }
    @Test
    fun `Una persona con email incorrecto NO debería poder crearse (Debe devolver un error para el email)`(){
        val persona: EitherNel<PersonaArgumentError, Persona> = Persona(nombreOK, edadOK, emailNOK, dniOK)
        assertTrue(persona.isLeft())
        assertEquals(1, persona.leftOrNull()?.size)
        assertTrue( persona.leftOrNull()?.contains(PersonaArgumentError.IllegalEmail)!!)
    }

}