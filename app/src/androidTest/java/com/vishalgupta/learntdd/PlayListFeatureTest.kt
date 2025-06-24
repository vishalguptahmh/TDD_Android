package com.vishalgupta.learntdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayListFeatureTest {
//    val mainActivityRule = ActivityScenarioRule(MainActivity::class.java)
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun playListTitleShown() {
        composeTestRule.onNodeWithText("Hello Android!").assertIsDisplayed()
//        assertDisplayed("Hello Android!")
    }

    @Test
    fun allPlaylistsAreDisplayed() {
        val playlistNames = listOf(
            "Hard Rock Cafe",
            "Chilled House",
            "US TOP 40 HITS",
            "90's Rock"
        )
        playlistNames.forEach { name ->
            composeTestRule.onNodeWithText(name).assertIsDisplayed()
        }
    }
}
//fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
//    return object : TypeSafeMatcher<View>() {
//        override fun describeTo(description: Description) {
//            description.appendText("position $childPosition of parent ")
//            parentMatcher.describeTo(description)
//        }
//
//        public override fun matchesSafely(view: View): Boolean {
//            if (view.parent !is ViewGroup) return false
//            val parent = view.parent as ViewGroup
//
//            return (parentMatcher.matches(parent)
//                && parent.childCount > childPosition
//                && parent.getChildAt(childPosition) == view)
//        }
//    }
//}