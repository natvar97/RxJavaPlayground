package com.indialone.rxjavaplayground.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val notes = MutableLiveData<List<NoteEntity>>()

    fun insert(note: NoteEntity) =
        repository.insert(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    fun getNotes() = Observable
        .fromCallable {
            repository.getAllNotes()
        }
        .subscribe { observable ->
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    notes.value = it
                }
        }

    fun getAllNotes(): LiveData<List<NoteEntity>> = notes

}