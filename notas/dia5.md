

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