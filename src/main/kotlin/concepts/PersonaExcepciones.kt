package concepts

class IllegalDNIException(message: String): IllegalArgumentException(message)
class IllegalNombreException(message: String): IllegalArgumentException(message)
class IllegalEdadException(message: String): IllegalArgumentException(message)
class IllegalEmailException(message: String): IllegalArgumentException(message)
class PersonaExcepcion /*constructor*/ (nombre:String, edad: Int, email: String, dni: String){
    // val mayorDeEdad: Boolean = edad >= 18
    init { // Damos código que se ejecuta al crearse una instancia de la clase
        // ME aseguro que el nombre tenga caracteres y empiece por mayúscula
        if (!nombre.matches(Regex("^[A-Z][a-záéíóúñ]+$"))) {
            throw IllegalNombreException("El nombre debe tener caracteres y empezar por mayúscula")
        }
        // Me aseguro que la edad sea mayor de 0
        if (edad < 0) {
            throw IllegalEdadException("La edad debe ser mayor de 0")
        }
        // Me aseguro que el email sea un email
        if (!email.matches(Regex("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$"))) {
            throw IllegalEmailException("El email debe ser un email")
        }
        // Me aseguro que el dni sea un dni
        if (!dni.matches(Regex("^[0-9]{1,8}[A-Z]$"))) {
            throw IllegalDNIException("El dni debe ser un dni")
        }
    }

}
// Result<T>
// Data class, nos ofrece una implementación por defecto de: equals(), hashCode, toString(), copy()
// Estos los usamos para DTOs

fun main(){
    // Crear una PersonaExcepcion
    val p1 = PersonaExcepcion("Pepe", 23, "ivan@pepe.com", "12345678A")

    try {
        val p2 = PersonaExcepcion("felipe", -17, "http://felipe.com", "ABCDEFGH1")
    }catch (e: IllegalNombreException) {
        println("Error al crear la PersonaExcepcion. Su nombre es inválido: ${e.message}")
    }catch (e: IllegalEdadException) {
        println("Error al crear la PersonaExcepcion. Su edad es inválida: ${e.message}")
    }catch (e: IllegalEmailException) {
        println("Error al crear la PersonaExcepcion. Su email es inválido: ${e.message}")
    }catch (e: IllegalDNIException) {
        println("Error al crear la PersonaExcepcion. Su DNI es inválido: ${e.message}")
    }catch (e: IllegalArgumentException) {
        println("Otro error al crear la PersonaExcepcion: ${e.message}")
    }
}

// Problemas:
// No queda EXPLICITO en la signatura de la función (constructor) los potenciales errores que se producen
// Necesitamos muchas excepciones diferentes para controlar cada tipo de error que hay
// La sintaxis de try/catch no es especialmente cómoda