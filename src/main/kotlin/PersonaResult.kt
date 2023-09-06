
interface PersonaResult{
    val nombre: String
    val edad: Int
    val email: String
    val dni: String

    private data class PersonaResultImpl(
        override val nombre: String,
        override val edad: Int,
        override val email: String,
        override val dni: String,
    ) : PersonaResult
    companion object {
        // INVOKE sobreescribe la llamada a la interfaz, como si fuera un constructor
        operator fun invoke(nombre: String, edad: Int, email: String, dni: String): Result<PersonaResult> = runCatching {

                // val mayorDeEdad: Boolean = edad >= 18
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
                PersonaResultImpl(nombre, edad, email, dni)
        }
    }
}
// Result<T>
// Data class, nos ofrece una implementación por defecto de: equals(), hashCode, toString(), copy()
// Estos los usamos para DTOs

fun main(){
    // Crear una Persona
    val p1: Result<PersonaResult> = PersonaResult("Pepe", 23, "ivan@pepe.com", "12345678A")
    if (p1.isSuccess) {
        println("Persona creada correctamente")
        println(p1.getOrNull())
    }else {
        println("Error al crear la persona: ${p1.exceptionOrNull()?.message}")
        when(p1.exceptionOrNull()) {
            is IllegalNombreException -> println("Error al crear la persona. Su nombre es inválido: ${p1.exceptionOrNull()?.message}")
            is IllegalEdadException -> println("Error al crear la persona. Su edad es inválida: ${p1.exceptionOrNull()?.message}")
            is IllegalEmailException -> println("Error al crear la persona. Su email es inválido: ${p1.exceptionOrNull()?.message}")
            is IllegalDNIException -> println("Error al crear la persona. Su DNI es inválido: ${p1.exceptionOrNull()?.message}")
            else -> println("Otro error al crear la persona: ${p1.exceptionOrNull()?.message}")
        }
    }
    val p2 = PersonaResult("felipe", -17, "http://felipe.com", "ABCDEFGH1")
    if (p2.isSuccess) {
        println("Persona creada correctamente")
        println(p2.getOrNull())
    }else {
        println("Error al crear la persona: ${p2.exceptionOrNull()?.message}")
        when(p2.exceptionOrNull()) {
            is IllegalNombreException -> println("Error al crear la persona. Su nombre es inválido: ${p2.exceptionOrNull()?.message}")
            is IllegalEdadException -> println("Error al crear la persona. Su edad es inválida: ${p2.exceptionOrNull()?.message}")
            is IllegalEmailException -> println("Error al crear la persona. Su email es inválido: ${p2.exceptionOrNull()?.message}")
            is IllegalDNIException -> println("Error al crear la persona. Su DNI es inválido: ${p2.exceptionOrNull()?.message}")
            else -> println("Otro error al crear la persona: ${p2.exceptionOrNull()?.message}")
        }
    }
}

// Esta es la solución GENERICA en KOTLIN para Excepciones

// Problemas:
// Sigo sin tener NI IDEA (NO QUEDA EXPLICITO POR NINGUN SITIO) de los TIPOS DE EXCEPCIONES
//     que se pueden lanzar. Solo sé que una función PUEDE SER que lance Excepciones... pero no cuales!
// Sigo necesitando muchos tipos de Exceptions
// Tengo que comprobar si hay excepción o no

// Las Excepciones SON MUY CARAS DE GENERAR! Hay que hacer un volcado de pila de hilos