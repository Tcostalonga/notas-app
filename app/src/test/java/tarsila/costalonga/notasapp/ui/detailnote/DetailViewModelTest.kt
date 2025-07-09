package tarsila.costalonga.notasapp.ui.detailnote

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.repository.NoteRepository
import tarsila.costalonga.notasapp.rules.InstantTaskRule

class DetailViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskRule()

    private val repository: NoteRepository = mockk()

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun `check if a note is being correctly loaded`() = runTest {
        val expectedNote = Note(
            id = 9293,
            title = "Title Note 1",
            description = "Description Note 1",
            createdAt = 4876,
            updatedAt = 5336,
            isFinished = false,
            sort = 1,
        )
        coEvery { repository.getNoteById(any()) } returns flowOf(expectedNote)

        viewModel.setNoteDetail(9293)

        assertThat(viewModel.title.text).isEqualTo("Title Note 1")
        assertThat(viewModel.description.text).isEqualTo("Description Note 1")
        coVerify(exactly = 1) { repository.getNoteById(9293) }
        confirmVerified(repository)
    }

    @Test
    fun `check if a note is being correctly deleted`() = runTest {
        coEvery { repository.deleteNote(any()) } just Runs

        viewModel.deleteNote()

        coVerify(exactly = 1) { repository.deleteNote(any()) }
        confirmVerified(repository)
    }

    @Test
    fun `check if a note is being correctly updated`() = runTest {
        coEvery { repository.updateNote(any()) } just Runs

        viewModel.updateNote()

        coVerify { repository.updateNote(any()) }
        confirmVerified(repository)

    }
}

