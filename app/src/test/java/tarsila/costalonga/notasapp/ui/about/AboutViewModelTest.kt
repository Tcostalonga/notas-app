package tarsila.costalonga.notasapp.ui.about

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.rules.InstantTaskRule
import tarsila.costalonga.notasapp.ui.about.compose.AboutUiIntents

class AboutViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskRule()

    private lateinit var viewModel: AboutViewModel

    @Before
    fun setUp() {
        viewModel = AboutViewModel()
    }

    @Test
    fun `check if timeline events uiState items are being correctly updated`() = runTest {
        viewModel.timelineEvents.test {

            val initialList = awaitItem().timelineEvents

            val updatedItem = initialList[0].copy(isExpanded = true)

            val updatedList = initialList.map {
                if (it.id == 1) {
                    updatedItem
                } else {
                    it
                }
            }

            viewModel.handleUiIntents(AboutUiIntents.OnExpandClick(1))

            assertThat(awaitItem().timelineEvents).isEqualTo(updatedList)
        }
    }

    @Test
    fun `check if aboutUiState is being correctly updated`() = runTest {
        viewModel.timelineEvents.test {

            val aboutUiState = awaitItem()

            val updatedAboutUiState = aboutUiState.copy(isAllTimelineExpanded = true)

            viewModel.handleUiIntents(AboutUiIntents.OnExpandAllClick)

            assertThat(awaitItem().isAllTimelineExpanded).isEqualTo(updatedAboutUiState.isAllTimelineExpanded)
        }
    }
}
