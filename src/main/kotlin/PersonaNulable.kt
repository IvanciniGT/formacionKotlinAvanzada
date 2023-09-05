interface PersonaNulable{
    val nombre: String
    val edad: Int
    val email: String
    val dni: String

    private data class PersonaNulableImpl(
        override val nombre: String,
        override val edad: Int,
        override val email: String,
        override val dni: String,
    ) : PersonaNulable
    companion object {
        // INVOKE sobreescribe la llamada a la interfaz, como si fuera un constructor
        operator fun invoke(nombre: String, edad: Int, email: String, dni: String): PersonaNulable? {
            // ME aseguro que el nombre tenga caracteres y empiece por mayúscula
            if (!nombre.matches(Regex("^[A-Z][a-záéíóúñ]+$"))) {
                return null;
            }
            // Me aseguro que la edad sea mayor de 0
            if (edad < 0) {
                return null;
            }
            // Me aseguro que el email sea un email
            if (!email.matches(Regex("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$"))) {
                return null;
            }
            // Me aseguro que el dni sea un dni
            if (!dni.matches(Regex("^[0-9]{1,8}[A-Z]$"))) {
                return null;
            }
            return PersonaNulableImpl(nombre, edad, email, dni)
        }
    }

}
fun main(){
    // Crear una Persona
    val p1 = PersonaNulable("Pepe", 23, "ivan@pepe.com", "12345678A")
    val p2 = PersonaNulable("felipe", -17, "http://felipe.com", "ABCDEFGH1")
    println(p1)
    if( p2 == null) {
        println("Error al crear la persona.")
    }else {
        // Persona creada
        println(p2)
    }
    println(p2?.nombre)
}

// Problemas:
// He perdido la información del tipo de error que se produce cuando devuelvo un null!

// Trabajar con nullables tiene sentido si algo se puede devolver o no... sin el hecho que de que no se devuelva por ocurrir un error:
// Por ejemplo: Pido a un Suministrador De Diccionarios: Dame el diccionario del Idioma de los Elfos.
// Si no lo tiene, no me lo da... pero no es un error... solo que no lo tiene ... y por ende, no me lo da
// El nullable : ?
//    es un equivalente DIRECTO del Optional de Java