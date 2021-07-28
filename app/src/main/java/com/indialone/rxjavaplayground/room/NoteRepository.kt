package com.indialone.rxjavaplayground.room

import io.reactivex.Flowable


class NoteRepository(
    private val noteDao: NoteDao
) {

    fun insert(note: NoteEntity) = noteDao.insert(note)

    fun getAllNotes(): Flowable<List<NoteEntity>> = noteDao.getAllNotes()

}