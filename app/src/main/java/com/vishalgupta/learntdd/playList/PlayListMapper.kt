package com.vishalgupta.learntdd.playList

import com.vishalgupta.learntdd.core.PlayListRaw

class PlayListMapper : Function1<List<PlayListRaw>, List<PlayList>> {
    override fun invoke(rawPlaylists: List<PlayListRaw>): List<PlayList> {
       return rawPlaylists.map {
           PlayList(it.id, it.name, it.category, it.image)
        }
    }
}