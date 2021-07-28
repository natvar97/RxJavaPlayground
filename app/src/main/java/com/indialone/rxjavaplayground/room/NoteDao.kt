package com.indialone.rxjavaplayground.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface NoteDao {

    @Insert
    fun insert(note: NoteEntity): Completable

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flowable<List<NoteEntity>>

}