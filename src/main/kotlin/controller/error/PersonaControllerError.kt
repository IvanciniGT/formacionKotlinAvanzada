package controller.error

import arrow.core.NonEmptyList
import model.error.PersonaArgumentError
import model.error.RepositorioError

class PersonaControllerError (

    var errorEnArgumentos: NonEmptyList<PersonaArgumentError>? = null,
    var errorEnRepositorio: RepositorioError? = null
)