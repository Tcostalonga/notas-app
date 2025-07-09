package tarsila.costalonga.notasapp.ui.statistics

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository
import tarsila.costalonga.notasapp.rules.InstantTaskRule

class StatisticsViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskRule()

    private val repository: NoteDataRepository = mockk()
    private lateinit var viewModel: StatisticsViewModel

    @Before
    fun setUp() {
        viewModel = StatisticsViewModel(
            repository = repository,
        )
    }

    @Test
    fun `check if uiState is being correctly updated`() = runTest {
        coEvery { repository.getNotesCount() } returns 5
        coEvery { repository.getDoneNotes() } returns 2

        viewModel.loadStatistics()

        viewModel.uiState.test {

            val result = awaitItem()

            assertThat(result.allNotes).isEqualTo(5)
            assertThat(result.allDoneNotes).isEqualTo(2)
            assertThat(result.allActiveNotes).isEqualTo(3)
        }
    }
}
