package com.vishalgupta.learntdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vishalgupta.learntdd.helper.BaseUITest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 *  Created by vishal.gupta on 26/06/25
 */

@RunWith(AndroidJUnit4::class)
class PlayListDetailFeatureTest : BaseUITest() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun displayItemDetailScreen() {
        composeTestRule.waitUntil ( timeoutMillis = 5000L){
            composeTestRule.onAllNodesWithTag("Loading").fetchSemanticsNodes().isEmpty()
        }
        composeTestRule.onNodeWithText("Hard Rock Cafe").performClick()
        composeTestRule.onNodeWithText("Hard Rock Cafe").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rock your senses with this timeless signature vibe list.").assertIsDisplayed()

    }
}