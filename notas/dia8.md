
# DAGGER

Nos ayudaba con la gestión de dependencias y con la inyección de dependencias.

## Anotaciones de dagger

@ Inject: Se usa para inyectar dependencias en una clase.

- Constructor
- Atributos/Propiedades

@Module: Se usa para crear clases que contienen métodos que son los que proporcionan las dependencias:

@Provides Para indicar nosotros mismos la instancia de la dependencia. 
@Binds    Para indicar que la instancia de la dependencia la proporciona una interfaz implementada con una clase que suministramos.

@Component: Se usa para crear interfaces que contienen métodos que son los que proporcionan las dependencias.

@Scope: Se usa para indicar que una dependencia es de ámbito (scope) determinado.
Con esta anotación le indicamos a Dagger... ómo debe gestionar el ciclo de vida de la dependencia.
- Hay dependencias que son singletons... y quiero una única instancia de ellas a lo largo de todo el ciclo de vida de la aplicación.

EJEMPLO:

Si creamos un Activity ... asociado al Activity, queremos un ViewModel.
Y es posible que queramos que Dagger sea quien cree el ViewModel... para que le inyecte dependencias.

RegistrationViewModel:
    Inyectando: UserManager
                PersonasService

Ese RegistrationViewModel... va asociado al Activity.
Cada vez que un activity se destruye, debe destruirse el ViewModel Correspondiente:
    @ActivityScope

---

#Hilt

Me hace la vida más sencilla al operar con Dagger en Android.

@HiltAndroidApp: Anotación que se usa en la clase Application de la app... para que arranque dagger

@AndroidEntryPoint: Anotación que se usa en las clases que queremos que Dagger sea capaz de injectar...
    OJO: No en las clases en las que quiero que dagger pueda hacer inyecciones
         En las clases que no siendo creadas por dagger, queremos que dagger pueda inyectar en otras clases

@HiltViewModel: Anotación para ViewModels... que asocia a la clase en automático un ActivityScope

@InstallIn: Anotación que se usa en las clases que queremos que dagger sea capaz de injectar... para indicarle en qué scope queremos que se inyecte.


Hay 2 tipos de instancias que dagger puede suministrar de forma automática:
- Las que el propio dagger crea < SCOPE
- Las que son creadas por otros y le decimos a dagger que es capaz de usar para inyecciones < SCOPE aquí no es posible... ya que dagger no crea esas instancias

  Qué hace dagger en este caso... Para las instancias creadas por otros, definimos un Subcomponent... / Module
    que indica que instancias deben entregarse
Y es a ese Subcomponent al que le ponemos el SCOPE  
