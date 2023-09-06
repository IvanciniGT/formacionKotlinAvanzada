

# Kotlin

Lenguaje que ofrece una sintaxis alternativa a JAVA.

## Lenguajes de Tipado estático vs Dinámico

- Tipado estático: Las variables tienen un tipo de datos asignando.
- Tipado dinámico: Las variables no tienen un tipo de datos asignando.

String texto = "hola";

    1. Creamos en memoria RAM un objeto de tipo TEXTO(String) con el valor "hola"
    2. Creamos una variable con el nombre "texto" y decimos que esa variable solo puede apuntar a objetos de tipo String
    3. Asignamos la variable al objeto (al valor)

texto = "adios";
1. Creamos en memoria RAM un objeto de tipo TEXTO(String) con el valor "adios"
En este momento, cuántas cosas hay en RAM? 2
2. Asignamos la variable al objeto (al valor)

## Paradigmas

- Imperativo            Escribimos órdenes que se procesan de forma secuencial: if, for, while
- Procedural            Cuando el lenguaje me permite definir mis propias funciones... e invocarlas posteriormente
  Dependiendo del lenguaje ... las llamamos: Procedimientos, Funciones, métodos, rutinas
- Orientado a objetos   Cuando el lenguaje me permite definir mis propios tipos de datos, con sus propiedades y funciones particulares
  clase, interfaz, objeto, herencia...
- Funcional             Cuando el lenguaje me permite que una variable referencie a una función, y posteriormente ejecutar esa función
  desde la variable.
  El concepto es sencillo. LA cosa es las consecuencias de esto.
  Desde el momento que puedo hacer eso, puedo tener(definir) funciones que reciban como argumentos otras funciones.
  O tener funciones que devuelvan funciones como resultado.

                        Típico en su uso las expresiones lambda (que se definen con el operador flecha en la mayor parte de lenguajes)

# Expresión lambda

Lo primero de todo es una EXPRESION.

```
String texto = "hola";          // Statement (Enunciado ~= Frase, Oración)
int numero = 7+9;               // Otro Statement
             ///  Esto es una expresión: Es una poción de código que devuelve un valor.
```

Una expresión lambda es un trozo de código que devuelve un valor... pero ese valor es: UNA FUNCION ANONIMA

Qué es una función anónima? Pues como su nombre indica... una función SIN NOMBRE!

---

# Inyección de dependencias

La inyección de dependencias es un PATRON de diseño en Orientación a Objetos... por el cuál
una instancia de una clase no crea instancias de otras clases, sino que le son suministradas.

La inyección de dependencias es una forma de conseguir respectar el principio de INVERSION DE DEPENDENCIAS

# PPO de inversión de dependencias

Es uno de los 5 principio SOLID de desarrollo de software.
Un componente de alto nivel no debe depender de una implementación de un componente de bajo nivel... solo de abstracciones (Interfaces)

---
# Libreria que permita trabajar con "Diccionarios"... que yo pueda preguntar si una palabra existe en un determinado idioma ... y sus significados

## La librería tendrá un API... (interfaces)

    interface Diccionario {

        boolean existe(String palabra);

        List<String> getSignificados(String palabra) throws NoSuchPalabraException;
            // Si pregunto por la palabra "manzana", me dará una lista de significados (LIST<STRING)>)
            // Si pregunto por la palabra "archilococo", me dará? npi!
                // Todos son una mierda gigante !!!

              - Devolver null           |   El comportamiento no queda EXPLICITO en la definición de la función (en el API)
                                            Esta opción al menos deja más claro la diferencia entre los 2 casos de uso posibles. 
              - Devolver lista vacia    |   El comportamiento no queda EXPLICITO en la definición de la función (en el API)

              - Lanzar una exception: NoSuchPalabraException        Es explicita !
                                                                    La función muestra 2 comportamiento claramente diferenciados:
                                                                        - Si la palabra existe
                                                                        - Si la palabra no existe 
                    El problema de esta es :
                         1. Nunca debería usar una exception para tratar lógica de código. try -> catch
                         2. Son muy caras de generar. Lo primero que se hace cuando hay una excepción es hacer un volcado del Thread Stack. ESTO ES CARO COMPACIONALMENTE !

        Optional<List<String>> getSignificados(String palabra);
            Un Optional es una caja, que puede llevar dentro un objeto o no.

    }

En Kotlin tenemos el operador "?", que entre otras cosas, nos permite definir que una función devuelve un dato de un tipo... o no!

            fun getSignificados(palabra: String): List<String>?;

class Usuario(val nombre: String){                  //Que tiene una longitud mayor que 0
init{
// Meto un if para comprobar la longitud
// Si tiene como longitud 0: Lanzo Exception
throw RuntimeException("No ha puesto el nombre, capullo!!!!!");
}
}

En Kotlin no puedo avisar que mi función va a lanzar una Exception... En la docu si ... pero que eso no lo mira nadie.
De nuevo queda algo NO EXPLICITO en código.
En Kotlin existe el tipo de dato: Result<>
Un Result es una caja que puede llevar dentro un dato del tipo especificado o un Throwable

Para usar el objeto Result deberíamos:

interface Usuario {
val nombre: String;

    companion object {
        // Aquí definimos lo que ej JAVA serían funciones STATIC
        // El companion object es un Singleton que se invoca con el mismo nombre de la clase o interfaz donde está definido
        fun crearUsuario(nombre:String): Result<Usuario> {
            if (nombre.length == 0)
                // Devuelvo un REsult con una Exception
            else
                return UsuarioImpl(nombre);
        }
    }
    private class UsuarioImpl (override val nombre: String): Usuario
}

Usuario.crearUsuario("Ivan");

Esta es la solución que se da en Kotlin a las Excepciones... el uso de Result<?>

Problema del Result.. trabaja con genéricos... y eso es bueno... pero solo para el dato devuelto si todo ha ido BIEN.
No hay forma que especifique el tipo de Exception que se puede generar (lanzar)

Aquí entra Arrow... O una de sus librerías (la más usada), que nos ofrece el tipo de datos: Either
Either<SI LAS COSAS VAN MAL, SI LAS COSAS VAN BIEN>

        fun crearUsuario(nombre:String): Either<CreacionUsuarioError,Usuario> 

Either me permite hacer EXPLICITO el tipo de errores que mi programa (función) puede generar.
Un objeto de tipo Either tiene un valor: IZQUIERDA = LEFT
un valor: DERECHA = RIGHT

val respuesta:Either<CreacionUsuarioError,Usuario> = Usuario.crearUsuario("Ivan");
IF: si la respuesta tiene IZQUIERDO jodido voy) ... y hago una cosa
ELSE: Todo ha ido bien... y hago otra!

Al API de Arrow le vamos a pasar una FUNCION con el código que debe ejecutarse si todo ha ido bien...
Y una función con el código que debe ejecutarse si algo ha ido mal.

Y AQUI ENTRA LA PROGRAMACION FUNCIONAL !

class GestionUsuarios {
procesarError(val error: CreacionUsuarioError) : Unit {
// Hace lo que sea
}
procesarGuay(val usuario: Usuario) : Unit {
// Hace lo que sea
}
}

// : Nothing (Se usa para funciones que no devuelven nada,.... porque no acaban de ejecutarse)



respuesta.fold( GestionUsuarios::procesarError , GestionUsuarios::procesarGuay )

        # Equivalente con Exceptions
        try{
            val usuario:Usuario = Usuario.crearUsuario("Ivan");
            GestionUsuarios.procesarGuay(usuario);
        }catch(TIPO DE EXCEPTION DE TURNO){
            GestionUsuarios.procesarError(EXCEPTION DE TURNO);
        }

.bind()

respuesta.fold( {} , {} )
respuesta.fold( {} ) {}         Esta linea en Kotlin es equivalente a la de arriba.. es lo mismo !!!!





inline fun <C> fold(

    ifLeft: (left: A) -> C, 
    ifRight: (right: B) -> C)
    
    : C


fun doble(val numero:Int) : Int {
return numero * 2
}

val miVariable: (Int) -> Int = {numero:Int -> numero * 2}


Proyecto: Servicio Backend con Kotlin:
^
AndroidStudio: App phone: |


---

Implementación del API de diccionarios.
- Podría tener una implementación que leyera los diccionarios y sus palabras de ficheros.
- Podría tener otra implementación que leyera los diccionarios y sus palabras de una BBDD.
- ... de un servicio web...

---
API: diccionario-desde-ficheros-1.0.0.jar

public class SuministradorDeDiccionariosDesdeFicheros implements SuministradorDeDiccionarios{
... Funciones con su código
}

public DiccionarioDesdeFichero implements Diccionario {
... Funciones con su código
}

---
API: diccionario-api-1.0.0.jar

interface Diccionario {
boolean existe(String palabra);
Optional<List<String>> getSignificados(String palabra);
}
interface SuministradorDeDiccionarios {
boolean tienesDiccionarioDe(String idioma);
Optional<Diccionario> getDiccionario(String idioma);
}
---

App que necesite saber si una palabra existe en un idioma?

import com.diccionarios.SuministradorDeDiccionarios;                                // Esto es una interfaz
// import com.diccionarios.ficheros.SuministradorDeDiccionariosDesdeFicheros;          // Esto es una implementación
// Aquí la acabamos de regar hasta lo más profundo del alma!
// Esta linea es la muerte del proyecto!
// Con esta linea nos acabamos de MEAR en el PRO de inversión de dependencias
// Acabo de atarme de pies y manos a esa implementación.
// Si la gente que implementa esa librería hace un cambio en ella (cambiar el nombre de la clase... cambiar la clase por otra)
// YO TENGO QUE CAMBIAR CODIGO DE MI APP... ESTO ES UN SIN SENTIDO

public class App {

    public void procesarPalabra(String palabra, String idioma, SuministradorDeDiccionarios suministrador ) { // Inyección de dependencias
        // hago cosas
        // Necesito saber si la palabra existe o no en el diccionario del idioma
        boolean existeLaPalabra = false;
        if(suministrador.tienesDiccionarioDe(idioma)) {
            existeLaPalabra = suministrador.getDiccionario(idioma).get().existe(palabra);
                                                                   /////
        }

        // Y luego sigo haciendo cosas
    }

}

// A esto nos ayudan los frameworks de Inyección de Dependencias
SuministradorDeDiccionarios -> new SuministradorDeDiccionariosDesdeFicheros();

---

# Gradle o Maven

Herramientas de automatización de tareas. Me permiten montar SCRIPTS de automatización... para:
- Gestión de dependencias
- Compilar y empaquetar el proyecto
- Ejecutar pruebas: Unitarias, Integración, Interfaz gráfica...
- Mandar un proyecto a SonarQube


# Testing

## Vocabulario en el mundo del testing

- Errores       Los humanos cometemos errores(por falta de conocimiento, prisas, cansancio....)
- Defectos      y al cometer un error introducimos DEFECTOS en los productos
- Fallos        que QUIZAS pueden manifestarse en algún momento en forma de FALLOS

## Para qué sirven las pruebas?

- Para asegurarme hoy y mañana que el producto cumple con unos requisitos
- Para identificar FALLOS antes del paso a producción
  Ante un fallo, DESARROLLO trata de identificar el DEFECTO que provoca el FALLO para subsanarlo (DEBUGGING)
- Para identificar DEFECTOS antes del paso a producción
- Analisis de causas raices, nos ayuda a identificar ERRORES, que den lugar a acciones preventivas que eviten nuevos ERRORES
  (y por ende defectos y fallos) en el futuro
- Para saber cómo voy en el proyecto
- En caso de FALLO, recopilar información que permita una RAPIDA identificación del DEFECTO
- ...

Sillabus ISQTB

## Metodología ágil?

El producto lo entregamos de forma incremental al cliente.

Entrega 1 -> 10% de la funcionalidad.... eso sí un 10% - 100% funcional.

    PRUEBAS A NIVEL DE PRODUCCION 10%

Entrega 2 -> +15% de la funcionalidad

    PRUEBAS A NIVEL DE PRODUCCION 15% nuevo + 10% antiguo

Entrega 3 -> + 5% de la funcionaldiad

    PRUEBAS A NIVEL DE PRODUCCION
....

Entrega n -> 100% de la funcionalidad o no?

Con las metodologías ágiles las pruebas se MULTIPLICAN !
De dónde saco la pasta para tanta prueba? NO HAY PASTA, NI RECURSOS, NI TIEMPO !

Tenemos un problema! Al que hemos encontrado UNA SOLA SOLUCION: AUTOMATIZACION


PUNTO DEL MANIFIESTO AGIL:
- El software funcionando es la medida principal de progreso.

La medida principal de progreso de un producto ( es decir, el grado de avance que tengo en el día a día) es "el software funcionando"
¿Qué tal vamos en el proyecto? Para saberlo, necesito una forma de medir el grado de avance en el proyecto!

Antiguamente:
1. Preguntar al desarrollador (NO ES FIABLE)
2. CONTAR EL NUMERO DE LINEAS DE CODIGO QUE UN DESARROLLO ESCRIBIA EN UNA UNIDAD DE TIEMPO (a la semana)

Hoy en día para determinar qué tal va un proyecto, usamos el concepto SOFTWARE FUNCIONANDO.

¿Quién dice que un software funciona?
LAS PRUEBAS -> Cuántas pruebas hemos superado esta semana.


---

# Tipos de pruebas:

Las pruebas TODAS se deben centrar en UNA UNICA CARACTERISTICA DE UN SISTEMA/COMPONENTE

# Nivel de la prueba

- Unitarias     (SON RESPONSABILIDAD DE DESARROLLO)
  Se centrar en una característica de un componente AISLADO del sistema
  Tren
  Motor       Sistema de transmisión      Ruedas          Freno

- Integración   Comprueban la COMUNICACION entre componentes
  FRENOS -> RUEDA

- Sistema       Se centran en el COMPORTAMIENTO DEL SISTEMA EN SU CONJUNTO

# Objeto de prueba

Funcionales: Se centran en la funcionaldiad
No funcionales : Se centran en otros aspectos
- Alta disponibilidad
- UX
- Estres
- Carga
- ...


JUNIT               Es una herramienta para automatizar PRUEBAS.
MOCKITO         Nos ayuda a crear TestDoubles (spy, mocks)

        Servicio de personas
        (Alta de persona)
            Guardar la persona en una BBDD              ----> Repositorio
            Mandar un email                             ----> Servicio asíncrono de envío de emails
        
        Si falla al dar de alta una persona... dónde puede estar el problema?
            - En la lógica del propio Servicio *         Quiero probar UNITARIAMENTE el servicio
            - En el repositorio
            - En el servicio de envío de emails
            - En la comunicación del servicio de personas con el respositorio
            - En la comunicación del servicio de personas con el servicio de emails...

        Pero para probar unitariamente el servicio, necesito AISLARLO del resto de componentes.

            CASO 1
                            Aislo el servicio de personas
                Servicio de personas   |     ---->       Repositorio

            CASO 2

                Servicio de personas   --->  A día de hoy, aún los que están haciendo el REPO no lo tienen acabado.
        
        Cuando trabajo con componentes que dependen de otros, para aislarlos sale el concepto de TEST-DOUBLES (genericamente llamados MOCKS)

# Test doubles

                    A --  argumentos    --> B
                      <-- devuelve algo----

- Stub              Monto código que devuelve una respuesta enlatada independentemente de los datos de entrada
- Fake              Cuando el stub lo voy haciendo más inteligente y devuelve respuestas en función de los datos de entrada
  Llevado al extremo, el fake se convierte en ... La implementación REAL
- Spy               En el spy, no es sólo que mande una respuesta enlatada (teniendo en cuenta los datos de entrada o no)
  Además controlo si se ha llamado al spy con los datos adecuados
- Mock              Es un spy inteligente... que sabe por si solo si la llamada es buena o no (normalmente marcandole una EXPECTATIVAS)



    Pantalla de Login       ----> Servicio de Autenticación (clase que tengo en mi proyecto Android) -> Backend
                                  doLogin(usuario:String, password:String): Either<ErroresLogin, DatosUsuario>
                                        = Either.Right(DatosUsuario("Felipe",33,"Administrador"))
                                        
                                        ^ STUB

                                  doLogin(usuario:String, password:String): Either<ErroresLogin, DatosUsuario>
                                    if(usuario == password)
                                        Either.Right(DatosUsuario("Felipe",33,"Administrador"))
                                    else
                                        Either.Left(CASTAÑAZO !)

                                        ^FAKE
                                
                                class ServicioAutenticacionSpy: ServicioAutenticacion{
                                    var numeroDeLlamadasALogin: Int;
                                    var usuarioUltimo: String
                                    var contraseñaUltima: String

                                    doLogin(usuario:String, password:String): Either<ErroresLogin, DatosUsuario>
                                        numerollamadas++;
                                        usuarioUltimo=usuario
                                        contyraseñaUltima=contraseña
                                        if(usuario == password)
                                            Either.Right(DatosUsuario("Felipe",33,"Administrador"))
                                        else
                                            Either.Left(CASTAÑAZO !)
                                }

----

Servidor Web con un servicio Web CRUD. (KTOR, ésto me monta en auto. el servidor web)

MODELO: interface Persona
con sus validaciones: ARROW

REPOSITORIO: interface PersonaRepository
guardar
buscarTodos
buscarUno
En memoria (con una List<Persona>)
* Usaremos ARROW por posibles problemas de comunicación con la BBDD

SERVICIO: (Aquí está la lógica de negocio)
altaPersona:
Validar los datos
Meter en el REPOSITORIO
Mandar un email
recuperarPersonas
recuperarPersona
* El servicio para poder funcionar, qué necesita? una instancia de un REPOSITORIO DE USUARIOS <<<< Inyección de dependencias (dagger)

CONTROLADOR:
Esto será la exposición de estas funciones de arriba (servicio) mediante HTTP
DTOs

        http://localhost:8080/api/v1/persona
            post
            get
        http://localhost:8080/api/v1/persona/{id}
            get
