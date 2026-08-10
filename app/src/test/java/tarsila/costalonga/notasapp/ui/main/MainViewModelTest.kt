package tarsila.costalonga.notasapp.ui.main

import android.content.SharedPreferences
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.data.repository.NoteRepository
import tarsila.costalonga.notasapp.rules.InstantTaskRule
import tarsila.costalonga.notasapp.ui.Dummy
import tarsila.costalonga.notasapp.ui.main.compose.MainEvent
import tarsila.costalonga.notasapp.ui.main.compose.MainIntent

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskRule()

    private val repository: NoteRepository = mockk()
    private val sharedPreferences: SharedPreferences = mockk()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        every { repository.getAllNotes() } returns flowOf(Dummy.listOfNotes)
        coEvery { sharedPreferences.getInt(any(), any()) } returns 2

        viewModel = MainViewModel(repository, sharedPreferences)
    }

    @Test
    fun `check if allNotes are being correctly loaded`() = runTest {
        val allNotes = Dummy.listOfNotes

        viewModel.noteListUiState.test {
            assertEquals(NoteListUiState.Loading, awaitItem())
            assertEquals(NoteListUiState.Success(allNotes), awaitItem())
        }
    }

    @Test
    fun `check if OnAddNoteClicked is correctly being called and updating uiState`() = runTest {
        viewModel.event.test {
            viewModel.handleIntent(MainIntent.OnAddNoteClick)

            assertThat(awaitItem()).isEqualTo(MainEvent.OnAddNoteClicked)
        }
    }

    @Test
    fun `check if OnCheckboxClick is correctly being called and updating uiState`() = runTest {
        coEvery { repository.updateNote(any()) } just Runs

        viewModel.handleIntent(MainIntent.OnCheckboxClick(Dummy.noteOne, true))
        advanceUntilIdle()

        val result = Dummy.noteOne.copy(isFinished = true)
        coVerify { repository.updateNote(result) }
    }
}
