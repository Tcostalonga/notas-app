package tarsila.costalonga.notasapp.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteComposeTheme
import tarsila.costalonga.notasapp.ui.detailnote.compose.DetailCompose

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun check_if_all_note_detail_info_is_being_correctly_shown() {
        composeTestRule.setContent {
            NoteComposeTheme {
                DetailCompose(
                    title = TextFieldState(Dummy.title),
                    description = TextFieldState(Dummy.description),
                    formattedDtCreated = Dummy.createdDate,
                    formattedDtUpdated = Dummy.updateDate,
                    onMenuClicked = {},
                    onFabClicked = {},
                )
            }
        }
        composeTestRule.onNode(hasText(Dummy.title)).assertIsDisplayed()
        composeTestRule.onNode(hasText(Dummy.description)).assertIsDisplayed()
        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.text_dtCriacao_format,
                    Dummy.createdDate,
                ),
            ),
        ).assertIsDisplayed()
        composeTestRule.onNode(
            hasText(
                composeTestRule.activity.getString(
                    R.string.text_dtAtualizado_format,
                    Dummy.updateDate,
                ),
            ),
        ).assertIsDisplayed()
    }

    @Test
    fun check_if_note_successfully_updated() {
        composeTestRule.setContent {
            NoteComposeTheme {
                DetailCompose(
                    title = TextFieldState(Dummy.title),
                    description = TextFieldState(Dummy.description),
                    formattedDtCreated = Dummy.createdDate,
                    formattedDtUpdated = Dummy.updateDate,
                    onMenuClicked = {},
                    onFabClicked = {},
                )
            }
        }
        composeTestRule.onNodeWithTag("DetailNoteFab").performClick()

        composeTestRule.onNode(hasText(Dummy.title)).performTextReplacement("Updated")

        composeTestRule.onNodeWithTag("DetailNoteFab").performClick()

        composeTestRule.onNode(hasText("Updated")).assertIsDisplayed()
    }
}
