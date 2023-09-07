package model.error

interface PersonaArgumentError : GenericError {
    object IllegalNombre : PersonaArgumentError
    object IllegalEdad: PersonaArgumentError
    object IllegalEmail : PersonaArgumentError
    object IllegalDni: PersonaArgumentError
}
