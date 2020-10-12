package tarsila.costalonga.notasapp.bd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import tarsila.costalonga.notasapp.getOrAwaitValue

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class NotasDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: NotasRoom
    private lateinit var dao: NotasDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotasRoom::class.java
        ).allowMainThreadQueries().build()
        dao = database.notasDao

    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNota() = runBlockingTest {
        val notaItem =
            Notas(id = 1, titulo = "Teste de titulo", anotacao = "Descrição da nota", ordem = 1)
        dao.insertNota(notaItem)

        val allNotasLD = dao.getAllNotas().getOrAwaitValue()

        assertThat(allNotasLD).contains(notaItem)
    }


    @Test
    fun deleteNota() = runBlockingTest {
        val notaItem =
            Notas(id = 1, titulo = "Teste de titulo", anotacao = "Descrição da nota", ordem = 1)
        dao.insertNota(notaItem)

        dao.deleteUmaNota(notaItem)

        val allNotasLD = dao.getAllNotas().getOrAwaitValue()

        assertThat(allNotasLD).doesNotContain(notaItem)
    }


    @Test
    fun updateNota() = runBlockingTest {
        val notaItem =
            Notas(id = 1, titulo = "Teste de titulo", anotacao = "Descrição da nota", ordem = 1)
        dao.insertNota(notaItem)

        notaItem.titulo = "Titulo dois"
        dao.updateNota(notaItem)

        assertThat(notaItem.titulo).matches("Titulo dois")

    }


}