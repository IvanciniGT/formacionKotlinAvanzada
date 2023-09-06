import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right

interface PersonaCreationError2 {
    val message: String
    data class IllegalNombreOnPersonaCreation(override val message: String) : PersonaCreationError2
    data class IllegalEdadOnPersonaCreation(override val message: String) : PersonaCreationError2
    data class IllegalEmailOnPersonaCreation(override val message: String) : PersonaCreationError2
    data class IllegalDniOnPersonaCreation(override val message: String) : PersonaCreationError2
}
interface PersonaEither2{
    val nombre: String
    val edad: Int
    val email: String
    val dni: String

    private data class PersonaEither2Impl(
        override val nombre: String,
        override val edad: Int,
        override val email: String,
        override val dni: String,
    ) : PersonaEither2
    companion object {
        // INVOKE sobreescribe la llamada a la interfaz, como si fuera un constructor
        operator fun invoke(nombre: String, edad: Int, email: String, dni: String): Either<PersonaCreationError2,PersonaEither2> = either {
                // val mayorDeEdad: Boolean = edad >= 18
                // ME aseguro que el nombre tenga caracteres y empiece por mayúscula
                ensure (nombre.matches(Regex("^[A-Z][a-záéíóúñ]+$"))) {
                    PersonaCreationError2.IllegalNombreOnPersonaCreation("El nombre debe tener caracteres y empezar por mayúscula")
                }
                // Me aseguro que la edad sea mayor de 0
                ensure (edad >= 0) {
                    PersonaCreationError2.IllegalEdadOnPersonaCreation("La edad debe ser mayor de 0")
                }
                // Me aseguro que el email sea un email
                ensure (email.matches(Regex("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$"))) {
                    PersonaCreationError2.IllegalEmailOnPersonaCreation("El email debe ser un email")
                }
                // Me aseguro que el dni sea un dni
                ensure (dni.matches(Regex("^[0-9]{1,8}[A-Z]$"))) {
                    PersonaCreationError2.IllegalDniOnPersonaCreation("El dni debe ser un dni")
                }
                // Devuelvo un Either con el valor de la derecha, el bueno. Esto es el caso: TODO HA IDO BIEN
                //return Either.Right(PersonaEither2Impl(nombre, edad, email, dni))
                PersonaEither2Impl(nombre, edad, email, dni) // Este dato se envuelve en el RIGHT del EITHER si no hay problemas, IGUAL que al trabajar con RESULTs
        }
    }
}
// Result<T>
// Data class, nos ofrece una implementación por defecto de: equals(), hashCode, toString(), copy()
// Estos los usamos para DTOs

fun main(){
    // Crear una Persona
    var p1: Either<PersonaCreationError2, PersonaEither2> = PersonaEither2("Pepe", 23, "ivan@pepe.com", "12345678A")
    p1 = PersonaEither2("felipe", -17, "http://felipe.com", "ABCDEFGH1")
    if (p1.isRight()) {
        println("Persona creada correctamente")
        println(p1.getOrNull()) // Esto devuelve el valor de la derecha
    }else {
        println("Error al crear la persona: ${p1.leftOrNull()?.message}")
        when(p1.leftOrNull()) {
            is PersonaCreationError2.IllegalNombreOnPersonaCreation -> println("Error al crear la persona. Su nombre es inválido: ${p1.leftOrNull()?.message}")
            is PersonaCreationError2.IllegalEdadOnPersonaCreation -> println("Error al crear la persona. Su edad es inválida: ${p1.leftOrNull()?.message}")
            is PersonaCreationError2.IllegalEmailOnPersonaCreation -> println("Error al crear la persona. Su email es inválido: ${p1.leftOrNull()?.message}")
            is PersonaCreationError2.IllegalDniOnPersonaCreation -> println("Error al crear la persona. Su dni es inválido: ${p1.leftOrNull()?.message}")
            else -> println("Otro error al crear la persona: ${p1.leftOrNull()?.message}")
        }
    }

    // Sintaxis alternativa
    p1.fold(
        {
            when (it) {
                is PersonaCreationError2.IllegalNombreOnPersonaCreation -> println("Error al crear la persona. Su nombre es inválido: ${it.message}")
                is PersonaCreationError2.IllegalEdadOnPersonaCreation -> println("Error al crear la persona. Su edad es inválida: ${it.message}")
                is PersonaCreationError2.IllegalEmailOnPersonaCreation -> println("Error al crear la persona. Su email es inválido: ${it.message}")
                is PersonaCreationError2.IllegalDniOnPersonaCreation -> println("Error al crear la persona. Su dni es inválido: ${it.message}")
                else -> println("Otro error al crear la persona: ${it.message}")
            }
        }
    ){
        println("Persona creada correctamente: ${it.nombre}") // En este caso it, hace referencia al RIGHT
    }




}

// Esta es la solución Que ofrece ARROW Como reemplazo de los Result de Kotlin para Excepciones

// DEJO DE TRABAJAR CON EXCEPCIONES
// Convierto los Errores en Datos que devuelvo en el Either
// Mucho más rápido (no hay volcado de la traza de la pila)

// a -> b -> c -> d

// Problemas:
// Sigo sin tener NI IDEA (NO QUEDA EXPLICITO POR NINGUN SITIO) de los TIPOS DE EXCEPCIONES
//     que se pueden lanzar. Solo sé que una función PUEDE SER que lance Excepciones... pero no cuales!
// Sigo necesitando muchos tipos de Exceptions
// Tengo que comprobar si hay excepción o no

// Las Excepciones SON MUY CARAS DE GENERAR! Hay que hacer un volcado de pila de hilos