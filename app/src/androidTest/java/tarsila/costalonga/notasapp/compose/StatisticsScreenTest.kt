package tarsila.costalonga.notasapp.compose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteComposeTheme
import tarsila.costalonga.notasapp.ui.statistics.StatisticsUiState
import tarsila.costalonga.notasapp.ui.statistics.compose.StatisticsCompose

class StatisticsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun check_if_active_notes_are_being_shown() {
        composeTestRule.setContent {
            NoteComposeTheme {
                StatisticsCompose(StatisticsUiState(40, 12, 28))
            }
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.notas_ativas))
            .assertTextEquals(composeTestRule.activity.getString(R.string.notas_ativas))

        composeTestRule
            .onNode(hasTestTag("NumberOfNotes") and hasText("28"))
            .assertIsDisplayed()
    }

    @Test
    fun check_if_all_notes_are_being_shown() {
        composeTestRule.setContent {
            NoteComposeTheme {
                StatisticsCompose(StatisticsUiState(40, 12, 28))
            }
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.notas_criadas))
            .assertTextEquals(composeTestRule.activity.getString(R.string.notas_criadas))

        composeTestRule
            .onNode(hasTestTag("NumberOfNotes") and hasText("40"))
            .assertIsDisplayed()
    }
}
