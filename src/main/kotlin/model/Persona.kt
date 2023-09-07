package model

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import model.error.PersonaArgumentError

interface Persona{
    var id: Int?
    var nombre: String
    var edad: Int
    var email: String
    var dni: String

    private data class PersonaImpl(
        override var id: Int?,
        override var nombre: String,
        override var edad: Int,
        override var email: String,
        override var dni: String,
    ) : Persona
    companion object {
        operator fun invoke(nombre: String, edad: Int, email: String, dni: String, id: Int? = null): EitherNel<PersonaArgumentError, Persona> = either {
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
            ) { _,_,_,_ -> PersonaImpl(id, nombre, edad, email, dni) }
        }
    }
}