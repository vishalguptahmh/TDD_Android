package com.vishalgupta.learntdd.playlist

import com.vishalgupta.learntdd.playList.PlayListMapper
import com.vishalgupta.learntdd.core.PlayListRaw
import com.vishalgupta.learntdd.utils.BaseUnitTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

/**
 *  Created by vishal.gupta on 25/06/25
 */
class PlayListMapperShould : BaseUnitTest() {

    val playListRaw : PlayListRaw = PlayListRaw("id1", "Test Playlist", "Category", 12345)
    val mapper = PlayListMapper()
    val playlists  = mapper(listOf(playListRaw))
    val playlist = playlists[0]

    @Test
    fun KeepSameId() = runTest {
        assertEquals(playListRaw.id, playlist.id)
    }

    @Test
    fun KeepSameName() = runTest {
        assertEquals(playListRaw.name, playlist.name)
    }

    @Test
    fun KeepSameCategory() = runTest {
        assertEquals(playListRaw.category, playlist.category)
    }

    @Test
    fun KeepSameImage() = runTest {
        assertEquals(playListRaw.image, playlist.image)
    }


}