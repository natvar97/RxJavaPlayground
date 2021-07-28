package com.indialone.rxjavaplayground.room

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indialone.rxjavaplayground.MyApplication
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val application: MyApplication
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(application.noteRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class found")
    }
}