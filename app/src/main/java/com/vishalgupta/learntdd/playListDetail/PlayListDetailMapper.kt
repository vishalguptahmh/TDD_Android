package com.vishalgupta.learntdd.playListDetail

import com.vishalgupta.learntdd.core.playlistDetail.PlayListDetailRaw

/**
 *  Created by vishal.gupta on 26/06/25
 */
class PlayListDetailMapper : Function1<PlayListDetailRaw, PlayListDetail>{
    override fun invoke(raw: PlayListDetailRaw): PlayListDetail {
        return PlayListDetail(
            id = raw.id,
            name = raw.name,
            details = raw.details,
        )
    }
}