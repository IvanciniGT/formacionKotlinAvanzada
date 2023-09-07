package controller.dto

class DatosPersonaDTO (
    var id: Int,
    override var nombre: String,
    override var edad: Int,
    override var email: String,
    override var dni: String
): DatosNuevaPersonaDTO(nombre, edad, email, dni)