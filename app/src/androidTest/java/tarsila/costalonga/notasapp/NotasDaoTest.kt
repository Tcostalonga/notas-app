package tarsila.costalonga.notasapp

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.bd.NotasDao
import tarsila.costalonga.notasapp.bd.NotasRoom
import tarsila.costalonga.notasapp.di.ModuleDatabase
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject
import javax.inject.Singleton
/*

@UninstallModules(ModuleDatabase::class)
@HiltAndroidTest
class AddFragTest {

    @Module
    @InstallIn(ApplicationComponent::class)
    abstract class TestModule {

        @Provides
        @Singleton
        fun proverDatabase(@ApplicationContext context: Context): NotasRoom {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                NotasRoom::class.java
            ).allowMainThreadQueries().build()
        }

        @Provides
        fun proverDao(dtBase: NotasRoom): NotasDao {
            return dtBase.notasDao
        }
    }
}


@HiltAndroidTest
class NotasDaoTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dao: NotasDao


    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun addNota(){
        val nota1 = Notas(titulo = "Title3", anotacao = "Description3")
        dao.insertNota(nota1)

    }


}*/
