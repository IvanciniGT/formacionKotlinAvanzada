

SERVIDOR                                               CLIENTE: App Android                             
---------------------------------------------          ---------------------------------------------
BBDD  <  REPOSITORIO < SERVICIO < CONTROLADOR < http < CLIENTE_HTTP < [ SERVICIO_BILLETES_TREN ] < X_VIEW_MODEL
                                         v                  v                                           v
                                   Objeto Kotlin(1)      JSON(1)                                    Objeto Kotlin (2)
                                         v                  v
                                        ktor               retrofit
                                         v                  v
                                        JSON(1)         Objeto Kotlin(1) -mapeo-> MiPropioObjetoKotlin(2)

Objeto Kotlin (1) -> JSON(1) <- RetroFit -> Objeto Kotlin (1) --> mapeo -->Objeto Kotlin (2)
    name               name                   name                           nombre
    lastName           lastName               lastName                       apellidos
                                                                             
    id                 id                     id                             dni
    birthdate          birthdate              birthdate         ---->        edad


---

# RETRO-FIT

Servicio REST:
    Ruta http a la que invoco (parametros)
    METODOS: GET, POST, PUT, DELETE, HEAD
        REQUEST -> BODY -> JSON?
        RESPONSE -> BODY -> JSON?
                    RESPONSE STATUS HTTP
                        200 -> SUCCESS
                        300 -> REDIRECCIONES
                        400 -> ERROR_CLIENTE (al hacer la petición)
                        500 -> ERROR_SERVIDOR (al procesar la petición)

Vamos a definir un interfaz... esa interfaz va a representar el servicio al que ataco... 
                           ... y en ella definiré los métodos que a mi me interesen para atacar al servicio


class DatosPersonaREST (
    var id: Int,
//    @JsonProperty("name")
    var nombre: String,
    var edad: Int,
    var email: String,
    var dni: String
)



interface PersonasServicio {
    @GET "/api/v1/user/"
    suspend fun recuperarTodasLasPersonas():Response<List<DatosPersonaREST>>;
    //@POST "/api/v1/user/"
    //@GET "/api/v1/user/{id}"
}

OAUTH

// suspend -> Corrutina

val retrofit:Retrofit = Retrofit.Builder()
                                                    .baseUrl("http://IP_DE_MI_MAQUINA:8080/")
                                                    .addConverterFactory(JacksonConverterFactory.create())
                                                                         GsonConverterFactory.create()
                                                    .build();
val cliente:PersonasServicio = retrofit.create(PersonasServicio::class.java)

# OAUTH

Token
    token
    refreshURL

## Autentica a la aplicación

ClientId
ClientSecret