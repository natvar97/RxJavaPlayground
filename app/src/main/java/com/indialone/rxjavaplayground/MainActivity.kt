package com.indialone.rxjavaplayground

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Pair
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.indialone.rxjavaplayground.databinding.ActivityMainBinding
import com.indialone.rxjavaplayground.room.NoteEntity
import com.indialone.rxjavaplayground.room.NoteViewModel
import com.indialone.rxjavaplayground.room.NotesRvAdapter
import com.indialone.rxjavaplayground.room.ViewModelFactory
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var compositeDisposable = CompositeDisposable()
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mNoteViewModel =
            ViewModelProvider(this, ViewModelFactory(application as MyApplication)).get(
                NoteViewModel::class.java
            )

        mBinding.fab.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        mNoteViewModel.getNotes()
        mNoteViewModel.getAllNotes().observe(this) {
            mBinding.rvNotes.layoutManager = LinearLayoutManager(this)
            mBinding.rvNotes.adapter = NotesRvAdapter(it as ArrayList<NoteEntity>)
        }
        // first create observable
        // we can map the observable using map operator

        val observable = Observable.range(1, 5)
            .doOnNext {
                Log.d(TAG, "range(): $it")
            }
            .map {
                it * 2
            }
            .doOnNext {
                Log.d(TAG, "map(): $it")
            }
            .filter {
                it < 8
            }
            .doOnNext {
                Log.d(TAG, "filter(): $it")
            }
            .subscribe()


    }


}


//        observable
//            .subscribe(object : Observer<Any> {
//                override fun onSubscribe(d: Disposable) {
//                    compositeDisposable.add(d)
//                }
//
//                override fun onNext(t: Any) {
//                    Log.d(TAG, "onNext: called : $t")
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//
//                override fun onComplete() {
//                    Log.d(TAG, "onComplete()")
//                }
//
//            })


/*
        create observable using create() method

                val observable: Observable<Int> = Observable
            .create(object : ObservableOnSubscribe<Int> {
                override fun subscribe(emitter: ObservableEmitter<Int>) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(1)
                        emitter.onNext(2)
                        emitter.onNext(3)
                        emitter.onComplete()
                    }
                }

            })

    ** output **
    D/MainActivity: onNext: called : 1
    onNext: called : 2
    onNext: called : 3

    * create() method with Any so that we can add any type of object in observable

    val observable = Observable
            .create<Any> { emitter ->
                emitter.onNext("text")
                emitter.onNext(2)
                emitter.onComplete()
            }

    ** output **
    D/MainActivity: onNext: called : text
    D/MainActivity: onNext: called : 2
        onComplete()

 */

/*
        * create observable using just() method

          val observable = Observable
            .just(1,2,3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        ** output **

        D/MainActivity: just() method
            onNext: called : 1
            just() method
            onNext: called : 2
            just() method
            onNext: called : 3

 */

/*
        ** mapping the observables using map() function

        val observable = Observable
                    .just(1,2,3)
                    .map {
                        it * 3
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
*
*
*           ** output
*           D/MainActivity: just() method
            onNext: called : 3
            just() method
            onNext: called : 6
            just() method
            onNext: called : 9
            *
            *
            *
            * another mapping

        *    val observable = Observable
                .just(4,5,6)
                .subscribeOn(Schedulers.io())
                .map {
                    ":)".repeat(it)
                }
                .observeOn(AndroidSchedulers.mainThread())

                ** output **
                D/MainActivity: onNext: called : :):):):)
                    onNext: called : :):):):):)
                    onNext: called : :):):):):):)

 * */

/*
        ** filtering
        *  val observable = Observable
            .just(1, 2, 3)
            .map {
                it * 3
            }
            .filter(object : Predicate<Int> {
                override fun test(t: Int): Boolean {
                    if (t % 6 == 0) return true
                    return false
                }

            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

*
*           ** output **
*
            D/MainActivity: filtering method
                onNext: called : 6

 */

/*
        ** observable using iterable
        *

        val list = listOf<String>("Uru", "Natu", "Hardy", "Parents")
        val observable = Observable
            .fromIterable(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        *
        *
        *
        * ** output **
        D/MainActivity: onNext: called : Uru
        D/MainActivity: onNext: called : Natu
            onNext: called : Hardy
            onNext: called : Parents

 */

/*

        takeWhile() operator
        * here i put a condition in takeWhile() method for value == "Hardy" it will return false
        * when takeWhile() method returns false it stops observing remaining observable data
        * we can use take() method to take only required elements for observing

        val list = listOf<String>("Uru", "Natu", "Hardy", "Parents")
        val observable = Observable
            .fromIterable(list)
            .subscribeOn(Schedulers.io())
            .takeWhile(object: Predicate<String>{
                override fun test(t: String): Boolean {
                    if (t == "Hardy") return false
                    return true
                }

            })
            .observeOn(AndroidSchedulers.mainThread())


        ** output **
        D/MainActivity: onNext: called : Uru
            onNext: called : Natu

        ** take() method
        val list = listOf<String>("Uru", "Natu", "Hardy", "Parents")
        val observable = Observable
            .fromIterable(list)
            .subscribeOn(Schedulers.io())
            .take(3)
            .observeOn(AndroidSchedulers.mainThread())

        ** output **
        D/MainActivity: onNext: called : Uru
            onNext: called : Natu
            onNext: called : Hardy

 */

/*
        ** merge operator **
        val list1 = listOf(1, 2, 3, 4, 5)
        val list2 = listOf(6, 7, 8)

        val observable1 = Observable
            .just(list1)

        val observable2 = Observable
            .just(list2)

        Observable.merge(observable1, observable2)
            .subscribe {
                for (item in it) {
                    Log.d(TAG, "$item")
                }
            }

        ** output **

        D/MainActivity: 1
            2
            3
            4
            5
            6
            7
            8

 */
/*
        *zip operator

        val list1 = listOf(1, 2, 3, 4, 5)
        val list2 = listOf(6, 7, 8)

        val observable1 = Observable
            .just(list1)

        val observable2 = Observable
            .just(list2)

        Observable.zip(observable1, observable2, { obser1, obser2 ->
            Pair(obser1, obser2)
        }).subscribe {
            for (item in it.first) {
                Log.d(TAG, "list1 items: $item")
            }
            for (item in it.second) {
                Log.d(TAG, "list2 items: $item")
            }
        }
        *
        ** output **
        D/MainActivity: list1 items: 1
            list1 items: 2
        D/MainActivity: list1 items: 3
            list1 items: 4
            list1 items: 5
            list2 items: 6
            list2 items: 7
            list2 items: 8
 */
/*
        * observable to list

        val observable = Observable.just(1, 2, 3, 4, 5).toList()
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                Log.d(TAG, "$list")
            }

         ** output **
         D/MainActivity: [1, 2, 3, 4, 5]
 */
/*
        doOnNExt


        val observable = Observable.range(1, 5)
            .doOnNext {
                Log.d(TAG, "range(): $it")
            }
            .map {
                it * 2
            }
            .doOnNext {
                Log.d(TAG, "map(): $it")
            }
            .filter {
                it < 8
            }
            .doOnNext {
                Log.d(TAG, "filter(): $it")
            }
            .subscribe()

            **output**
            D/MainActivity: range(): 1
                map(): 2
                filter(): 2
                range(): 2
                map(): 4
                filter(): 4
                range(): 3
                map(): 6
            D/MainActivity: filter(): 6
                range(): 4
                map(): 8
                range(): 5
                map(): 10

 */