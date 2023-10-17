import controller.PersonaController
import controller.PersonaControllerImpl
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import repository.PersonaRepository
import repository.PersonaRepositoryImpl
import service.PersonaServicio
import service.PersonaServicioImpl
import javax.inject.Singleton

@Module
//abstract
class Injections{
    @Provides
    fun injectPersonaRepository(): PersonaRepository {
        return PersonaRepositoryImpl()
    }
//    @Binds
//    abstract fun injectPersonaRepository(aDevolver:PersonaRepositoryImpl): PersonaRepository;

    @Provides
    fun injectPersonaService(personaRepository: PersonaRepository): PersonaServicio {
        return PersonaServicioImpl(personaRepository)
    }
    @Provides
    fun injectPersonaController(personaServicio: PersonaServicio): PersonaController {
        return PersonaControllerImpl(personaServicio)
    }
}

@Singleton
@Component(modules = [Injections::class])
interface PersonaControllerComponent{
    fun getPersonaController(): PersonaController
}
