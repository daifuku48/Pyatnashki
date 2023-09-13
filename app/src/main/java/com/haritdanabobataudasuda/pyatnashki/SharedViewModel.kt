package com.haritdanabobataudasuda.pyatnashki

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private var time = MutableLiveData("")

    fun setTime(s: String){
        time.value = s
    }

    fun getTime() = time.value
}