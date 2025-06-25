package com.vishalgupta.learntdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayListFeatureTest : BaseUITest(){
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun playListTitleShown() {
        composeTestRule.onNodeWithText("Hello Android!").assertIsDisplayed()
    }

    @Test
    fun allPlaylistsAreDisplayed() {
        composeTestRule.waitUntil ( timeoutMillis = 5000L){
            composeTestRule.onAllNodesWithTag("Loading").fetchSemanticsNodes().isEmpty()
        }
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

    @Test
    fun DisplayLoaderWhileFetchingPlayList() {
        composeTestRule.onNodeWithTag("Loading").assertIsDisplayed()

    }


    @Test
    fun displayRockImagesForRockListItems() {


    }
}