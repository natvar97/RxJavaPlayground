package com.indialone.rxjavaplayground

import android.app.Application
import com.indialone.rxjavaplayground.room.NoteDatabase
import com.indialone.rxjavaplayground.room.NoteRepository

class MyApplication : Application() {

    private val database by lazy {
        NoteDatabase.getInstance(this)
    }

    val noteRepository: NoteRepository by lazy {
        NoteRepository(database!!.noteDao())
    }

}