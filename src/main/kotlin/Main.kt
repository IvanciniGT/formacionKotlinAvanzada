/*fun doblar( numero: Int ) : Int{
    return numero * 2
}
*/
fun doblar( numero: Int ) = numero * 2

fun main() {
    println("Doble del 8: ${doblar(8)}")

    val miFuncion: (Int) -> Int = ::doblar
    // (Int) -> Int es el tipo de dato de la variable miFuncion
    //  ^               Los tipos de datos de los argumentos de mi función
    //           ^      El tipo de dato que devuelve mi función
    val texto: String = "Hola";
    // String es el tipo de dato de la variable texto
    println("Doble del 7: ${miFuncion(7)}")

    // val miFuncion2 = { numero: Int -> { numero * 2 } } Esto es lo mismo
    // val miFuncion2 = { numero: Int -> numero * 2 }
    val miFuncion2 = { numero: Int -> {
                                            val resultado = numero * 2
                                            resultado // No ponemos la palabra return
                                      }()
                     }
    println("Doble del 5: ${miFuncion2(5)}")

    val miFuncion3 = fun (numero: Int): Int {
        return numero * 2 // Esta sintaxis si nos obliga a poner la palabra RETURN
    }
    println("Doble del 4: ${miFuncion3(4)}")


    //val miFuncion4 = { it * 2 }


    // MAP
    val numeros = listOf(1,2,3,4,5)
    // Quiero una lista con el doble de esos números
    //val dobles = MutableList(numeros.size) { 0 }
    val dobles = MutableList(numeros.size)  { _: Int -> 0 }
    for (i in numeros.indices) {
        dobles[i] = numeros[i] * 2
    }
    // (1  ,2  ,3  ,4  ,5)
    //      |
    //      x2
    //      v
    // (2  ,4  ,6  ,8  ,10)
    // val dobles2 = numeros.map( ::doblar )
    // val dobles2 = numeros.map( fun(numero:Int) = numero * 2 )   // Función anónima
    // val dobles2 = numeros.map( { numero:Int -> numero * 2 } ) // Lambda
    // val dobles2 = numeros.map( { it * 2 } ) // Lambda con inferencia del tipo de datos de entrada
    // val dobles2 = numeros.map() { it * 2 }  // Lambda con inferencia del tipo de datos de entrada
    val dobles2 = numeros.map { it * 2 }
    println("Dobles: $dobles2")
}