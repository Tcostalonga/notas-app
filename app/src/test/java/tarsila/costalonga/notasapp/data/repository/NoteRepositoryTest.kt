package tarsila.costalonga.notasapp.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.local.NoteDao
import tarsila.costalonga.notasapp.rules.InstantTaskRule

class NoteRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskRule()

    private val dao: NoteDao = mockk()

    private lateinit var repository: NoteRepository

    private val stubNote = Note(
        id = 3777,
        title = "Lorem ipsum dolor",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, ",
        createdAt = 6389,
        updatedAt = 3947,
        isFinished = false,
        sort = 1782,
    )

    @Before
    fun setUp() {
        repository = NoteDataRepository(dao)
    }

    @Test
    fun `should insertNote runs successfully`() = runTest {
        coEvery { dao.insertNote(any()) } just Runs

        repository.insertNote(stubNote)

        coVerify(exactly = 1) { dao.insertNote(stubNote) }
    }

    @Test
    fun `should updateNote runs successfully`() = runTest {
        coEvery { dao.updateNote(any()) } just Runs

        repository.updateNote(stubNote)

        coVerify(exactly = 1) { dao.updateNote(stubNote) }
    }

    @Test
    fun `should deleteNote runs successfully`() = runTest {
        coEvery { dao.deleteNote(any()) } just Runs

        repository.deleteNote(stubNote)

        coVerify(exactly = 1) { dao.deleteNote(stubNote) }
    }

    @Test
    fun `should getAllNotes runs successfully`() = runTest {
        val expected = flowOf(listOf(stubNote))
        coEvery { dao.getAllNotes() } returns expected

        val result = repository.getAllNotes()

        coVerify(exactly = 1) { dao.getAllNotes() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should getNoteById runs successfully`() = runTest {
        val expected = flowOf(listOf(stubNote))
        coEvery { dao.getAllNotes() } returns expected

        val result = repository.getAllNotes()

        coVerify(exactly = 1) { dao.getAllNotes() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should getDoneNotes runs successfully`() = runTest {
        val expected = flowOf(listOf(stubNote))
        coEvery { dao.getAllNotes() } returns expected

        val result = repository.getAllNotes()

        coVerify(exactly = 1) { dao.getAllNotes() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should getActiveNotes runs successfully`() = runTest {
        val expected = 4
        coEvery { dao.getActiveNotes() } returns expected

        val result = repository.getActiveNotes()

        coVerify(exactly = 1) { dao.getActiveNotes() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should getLastItemId runs successfully`() = runTest {
        val expected = 123L
        coEvery { dao.getLastItemId() } returns expected

        val result = repository.getLastItemId()

        coVerify(exactly = 1) { dao.getLastItemId() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should getNotesCount runs successfully`() = runTest {
        val expected = 5
        coEvery { dao.getNotesCount() } returns expected

        val result = repository.getNotesCount()

        coVerify(exactly = 1) { dao.getNotesCount() }
        assertThat(result).isEqualTo(expected)
    }
}
