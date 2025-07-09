package tarsila.costalonga.notasapp.ui.addnote

import android.content.SharedPreferences
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.repository.NoteRepository
import tarsila.costalonga.notasapp.rules.InstantTaskRule

class AddViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskRule()

    private val repository: NoteRepository = mockk()
    private val sharedPreferences: SharedPreferences = mockk()

    private lateinit var viewModel: AddViewModel

    @Before
    fun setUp() {
        viewModel = AddViewModel(repository, sharedPreferences)
    }

    @Test
    fun `check addNote() will fail if title or description is empty`() = runTest {
        viewModel.addNoteEvents.test {

            viewModel.titleTextFieldState.edit {
                this.append("Title")
            }

            viewModel.addNote()

            val event = awaitItem()
            assertThat(event).isEqualTo(AddNoteEvents.UnableToCreateNote)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `check addNote() will succeed if title and description are filled`() = runTest {
        Note(
            title = "Title",
            description = "",
            sort = 3,
        )
        coEvery { repository.getLastItemId() } returns 2
        coEvery { repository.insertNote(any()) } just Runs

        every { viewModel.clearSharedPreferences() } just Runs

        viewModel.titleTextFieldState.edit {
            this.append("Title")
        }

        viewModel.descriptionTextFieldState.edit {
            this.append("Description")
        }

        viewModel.addNoteEvents.test {

            viewModel.addNote()

            val event = awaitItem()
            assertThat(event).isEqualTo(AddNoteEvents.NoteSuccessfullyCreated)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `check if hideSketch() is correctly updating the value of _showSketchAlert`() = runTest {
        viewModel.showSketchAlert.test {

            viewModel.hideSketchAlert()

            assertThat(awaitItem()).isEqualTo(false)
        }
    }
}
