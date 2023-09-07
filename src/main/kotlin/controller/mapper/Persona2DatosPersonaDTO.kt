package controller.mapper

import controller.dto.DatosPersonaDTO
import model.Persona


fun persona2DatosPersonaDTO (
            persona: Persona
        ): DatosPersonaDTO =
            DatosPersonaDTO(
                id = persona.id!!,
                nombre = persona.nombre,
                edad = persona.edad,
                email = persona.email,
                dni = persona.dni
            )
