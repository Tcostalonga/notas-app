@file:OptIn(ExperimentalTestApi::class)

package tarsila.costalonga.notasapp.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.addnote.compose.AddNoteCompose
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteComposeTheme

class AddNoteScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun check_if_add_note_succeeds() {
        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            NoteComposeTheme {
                AddNoteCompose(
                    titleState = TextFieldState(),
                    descriptionState = TextFieldState(),
                    focusRequester = remember { FocusRequester() },
                    onFabClicked = {},
                )
            }
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.titulo))
            .performClick()
            .performTextInput("Hello world, my first note")

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.anotacao))
            .performClick()
            .performTextInput("Hello world, my first note with description")

        composeTestRule.onNodeWithTag("AddNoteFab").performClick()
    }
}
