package concepts

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right

interface PersonaCreationError3 {
    val message: String
    data class IllegalNombreOnPersonaCreation(override val message: String) : PersonaCreationError3
    data class IllegalEdadOnPersonaCreation(override val message: String) : PersonaCreationError3
    data class IllegalEmailOnPersonaCreation(override val message: String) : PersonaCreationError3
    data class IllegalDniOnPersonaCreation(override val message: String) : PersonaCreationError3
}
interface PersonaEither{
    val nombre: String
    val edad: Int
    val email: String
    val dni: String

    private data class PersonaEitherImpl(
        override val nombre: String,
        override val edad: Int,
        override val email: String,
        override val dni: String,
    ) : PersonaEither
    companion object {

        fun validarNombre(nombre: String): Either<PersonaCreationError3, String> {
            if (!nombre.matches(Regex("^[A-Z][a-záéíóúñ]+$"))) {
                return PersonaCreationError3.IllegalNombreOnPersonaCreation("El nombre debe tener caracteres y empezar por mayúscula")
                    .left()
            }
            return nombre.right()
        }
        fun validarEdad(edad: Int): Either<PersonaCreationError3, Int> = either{
            ensure (edad > 0) {
                PersonaCreationError3.IllegalEdadOnPersonaCreation("La edad debe ser mayor de 0")
            }
            edad
        }
        fun validarEmail(email: String): Either<PersonaCreationError3, Unit> = either{
            ensure (email.matches(Regex("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$"))) {
                PersonaCreationError3.IllegalEmailOnPersonaCreation("El email debe ser un email")
            }
        }
        fun validarDni(dni: String): Either<PersonaCreationError3, String> {
            if (!dni.matches(Regex("^[0-9]{1,8}[A-Z]$"))) {
                return PersonaCreationError3.IllegalDniOnPersonaCreation("El dni debe ser un dni").left()
            }
            return dni.right()
        }
        // INVOKE sobreescribe la llamada a la interfaz, como si fuera un constructor
        operator fun invoke(nombre: String, edad: Int, email: String, dni: String): Either<PersonaCreationError3, PersonaEither>  =
/*
                // val mayorDeEdad: Boolean = edad >= 18
                // ME aseguro que el nombre tenga caracteres y empiece por mayúscula
                if (!nombre.matches(Regex("^[A-Z][a-záéíóúñ]+$"))) {
                    return PersonaCreationError3.IllegalNombreOnPersonaCreation("El nombre debe tener caracteres y empezar por mayúscula").left()
                }
                // Me aseguro que la edad sea mayor de 0
                if (edad < 0) {
                    return PersonaCreationError3.IllegalEdadOnPersonaCreation("La edad debe ser mayor de 0").left()
                }
                // Me aseguro que el email sea un email
                if (!email.matches(Regex("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$"))) {
                    return Either.Left( PersonaCreationError3.IllegalEmailOnPersonaCreation("El email debe ser un email"))
                }
                // Me aseguro que el dni sea un dni
                if (!dni.matches(Regex("^[0-9]{1,8}[A-Z]$"))) {
                    return Either.Left( PersonaCreationError3.IllegalDniOnPersonaCreation("El dni debe ser un dni"))
                }
                // Devuelvo un Either con el valor de la derecha, el bueno. Esto es el caso: TODO HA IDO BIEN
                //return Either.Right(PersonaEitherImpl(nombre, edad, email, dni))
                return PersonaEitherImpl(nombre, edad, email, dni).right()
*/
                validarNombre(nombre)
                    .flatMap { validarEdad(edad) }      // Either(ERROR_A, _ ) -> Either(ERROR_B, _)
                                                       // Solo se ejecuta el flatMap si no ha habido error. Si hay error, no se ejecuta
                    .flatMap { validarEmail(email) }
                    .flatMap { validarDni(dni) }
                    // Si llegados a este punto no hay error: Devuelvo la persona
                                                        // Either(_, DATO_A) -> Either(_, DATO_B)
                    .map    { PersonaEitherImpl(nombre, edad, email, it /*dni*/)

        }
    }
}
// Result<T>
// Data class, nos ofrece una implementación por defecto de: equals(), hashCode, toString(), copy()
// Estos los usamos para DTOs

fun main(){
    // Crear una Persona
    var p1: Either<PersonaCreationError3, PersonaEither> = PersonaEither("Pepe", 23, "ivan@pepe.com", "12345678A")
    p1 = PersonaEither("felipe", -17, "http://felipe.com", "ABCDEFGH1")
    if (p1.isRight()) {
        println("Persona creada correctamente")
        println(p1.getOrNull()) // Esto devuelve el valor de la derecha
    }else {
        println("Error al crear la persona: ${p1.leftOrNull()?.message}")
        when(p1.leftOrNull()) {
            is PersonaCreationError3.IllegalNombreOnPersonaCreation -> println("Error al crear la persona. Su nombre es inválido: ${p1.leftOrNull()?.message}")
            is PersonaCreationError3.IllegalEdadOnPersonaCreation -> println("Error al crear la persona. Su edad es inválida: ${p1.leftOrNull()?.message}")
            is PersonaCreationError3.IllegalEmailOnPersonaCreation -> println("Error al crear la persona. Su email es inválido: ${p1.leftOrNull()?.message}")
            is PersonaCreationError3.IllegalDniOnPersonaCreation -> println("Error al crear la persona. Su dni es inválido: ${p1.leftOrNull()?.message}")
            else -> println("Otro error al crear la persona: ${p1.leftOrNull()?.message}")
        }
    }

    // Sintaxis alternativa
    p1.fold(
        {
            when (it) {
                is PersonaCreationError3.IllegalNombreOnPersonaCreation -> println("Error al crear la persona. Su nombre es inválido: ${it.message}")
                is PersonaCreationError3.IllegalEdadOnPersonaCreation -> println("Error al crear la persona. Su edad es inválida: ${it.message}")
                is PersonaCreationError3.IllegalEmailOnPersonaCreation -> println("Error al crear la persona. Su email es inválido: ${it.message}")
                is PersonaCreationError3.IllegalDniOnPersonaCreation -> println("Error al crear la persona. Su dni es inválido: ${it.message}")
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