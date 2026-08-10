@file:OptIn(ExperimentalCoroutinesApi::class)

package tarsila.costalonga.notasapp.ui.detailnote

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.data.repository.NoteRepository
import tarsila.costalonga.notasapp.rules.InstantTaskRule
import tarsila.costalonga.notasapp.ui.Dummy

class DetailViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskRule(UnconfinedTestDispatcher())

    private val repository: NoteRepository = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    private val viewModel: DetailViewModel by lazy {
        DetailViewModel(
            repository,
            savedStateHandle,
        )
    }

    @Before
    fun setUp() {
        every { savedStateHandle.get<Long>("noteId") } returns 9293
        coEvery { repository.getNoteById(any()) } returns flowOf(Dummy.expectedNote)
    }

    @Test
    fun `check if a note is being correctly loaded`() = runTest {

        viewModel.noteDetail.test {

            val item = awaitItem()
            assertEquals(Dummy.expectedNote, item)
            assertEquals("Title Note 1", viewModel.title.text.toString())
            assertEquals("Description Note 1", viewModel.description.text.toString())

        }
    }

    @Test
    fun `check if a note is being correctly deleted`() = runTest {
        coEvery { repository.deleteNote(any()) } just Runs

        viewModel.deleteNote()

        coVerify { repository.deleteNote(any()) }
    }

    @Test
    fun `check if a note is being correctly updated`() = runTest {
        coEvery { repository.updateNote(any()) } just Runs

        viewModel.updateNote()

        coVerify { repository.updateNote(any()) }
    }
}

