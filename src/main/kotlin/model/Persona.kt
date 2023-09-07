package model

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import model.error.PersonaArgumentError

interface Persona{
    val nombre: String
    val edad: Int
    val email: String
    val dni: String

    private data class PersonaImpl(
        override val nombre: String,
        override val edad: Int,
        override val email: String,
        override val dni: String,
    ) : Persona
    companion object {
        operator fun invoke(nombre: String, edad: Int, email: String, dni: String): EitherNel<PersonaArgumentError, Persona> = either {
            zipOrAccumulate(
                { ensure (nombre.matches(Regex("^[A-Z][a-záéíóúñ]+$"))) {
                    PersonaArgumentError.IllegalNombre
                }},
                {ensure (edad >= 0) {
                    PersonaArgumentError.IllegalEdad
                }},
                {ensure (email.matches(Regex("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$"))) {
                    PersonaArgumentError.IllegalEmail
                }},
                {ensure (dni.matches(Regex("^[0-9]{1,8}[A-Z]$"))) {
                    PersonaArgumentError.IllegalDni
                }}
            ) { _,_,_,_ -> PersonaImpl(nombre, edad, email, dni) }
        }
    }
}