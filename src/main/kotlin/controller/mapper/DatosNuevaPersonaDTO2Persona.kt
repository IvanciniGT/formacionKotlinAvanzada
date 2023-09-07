package controller.mapper

import arrow.core.Either
import arrow.core.EitherNel
import controller.dto.DatosNuevaPersonaDTO
import model.Persona
import model.error.PersonaArgumentError

fun datosNuevaPersonaDTO2Persona (
    datosNuevaPersonaDTO: DatosNuevaPersonaDTO
): EitherNel<PersonaArgumentError, Persona> =
    Persona(
        nombre = datosNuevaPersonaDTO.nombre,
        edad = datosNuevaPersonaDTO.edad,
        email = datosNuevaPersonaDTO.email,
        dni = datosNuevaPersonaDTO.dni
    )
