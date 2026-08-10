@file:OptIn(ExperimentalCoroutinesApi::class)

package tarsila.costalonga.notasapp.ui.statistics

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository
import tarsila.costalonga.notasapp.rules.InstantTaskRule

class StatisticsViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskRule()

    private val repository: NoteDataRepository = mockk()

    private val viewModel: StatisticsViewModel by lazy {
        StatisticsViewModel(repository)
    }

    @Test
    fun `check if uiState is being correctly updated`() = runTest {
        coEvery { repository.getNotesCount() } returns 5
        coEvery { repository.getDoneNotes() } returns 2

        viewModel.uiState.test {
            awaitItem()
            val result = awaitItem()

            assertThat(result.allNotes).isEqualTo(5)
            assertThat(result.allDoneNotes).isEqualTo(2)
            assertThat(result.allActiveNotes).isEqualTo(3)
        }
    }
}
