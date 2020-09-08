package tarsila.costalonga.notasapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddAnotacaoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addAnotacaoTest() {
        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab_add), withContentDescription("Criar anotação"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.add_titulo),
                childAtPosition(
                    allOf(
                        withId(R.id.linear_layout_add),
                        childAtPosition(
                            withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.add_titulo),
                childAtPosition(
                    allOf(
                        withId(R.id.linear_layout_add),
                        childAtPosition(
                            withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Til"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.add_titulo), withText("Til"),
                childAtPosition(
                    allOf(
                        withId(R.id.linear_layout_add),
                        childAtPosition(
                            withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(pressImeActionButton())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.add_anotacao),
                childAtPosition(
                    allOf(
                        withId(R.id.linear_layout_add),
                        childAtPosition(
                            withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("Descrição"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.add_anotacao), withText("Descrição"),
                childAtPosition(
                    allOf(
                        withId(R.id.linear_layout_add),
                        childAtPosition(
                            withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(click())

        val editText = onView(
            allOf(
                withId(R.id.add_titulo), withText("Til"),
                childAtPosition(
                    allOf(
                        withId(R.id.linear_layout_add),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        editText.check(matches(withText("Til")))

        val editText2 = onView(
            allOf(
                withId(R.id.add_anotacao), withText("Descrição"),
                childAtPosition(
                    allOf(
                        withId(R.id.linear_layout_add),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        editText2.check(matches(withText("Descrição")))

        val imageButton = onView(
            allOf(
                withId(R.id.save_task_fab), withContentDescription("Salvar nota"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.save_task_fab), withContentDescription("Salvar nota"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
