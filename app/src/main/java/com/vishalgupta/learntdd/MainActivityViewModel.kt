package com.vishalgupta.learntdd

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  Created by vishal.gupta on 23/06/25
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(repository: PlayListRepository) : ViewModel() {

//    val playList: StateFlow<Result<List<PlayList>>?> = repository.getPlayList()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Lazily,
//            initialValue = null
//        )



    private val _playList = MutableStateFlow<Result<List<PlayList>>?>(null)
    val playList: StateFlow<Result<List<PlayList>>?> = _playList

    init {
        viewModelScope.launch {
            repository.getPlayList().collect {
                _playList.value = it
            }
        }
    }
}