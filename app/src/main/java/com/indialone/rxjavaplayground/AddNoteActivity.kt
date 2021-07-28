package com.indialone.rxjavaplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.indialone.rxjavaplayground.databinding.ActivityAddNoteBinding
import com.indialone.rxjavaplayground.room.NoteEntity
import com.indialone.rxjavaplayground.room.NoteViewModel
import com.indialone.rxjavaplayground.room.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddNoteActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddNoteBinding
    private var title: String = ""
    private var description: String = ""
    private lateinit var mNoteViewModel: NoteViewModel
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mNoteViewModel =
            ViewModelProvider(this, ViewModelFactory(application as MyApplication)).get(
                NoteViewModel::class.java
            )

        mBinding.btnSave.setOnClickListener {
            if (validateNotesDetails()) {
                val note = NoteEntity(title = title, description = description)

                compositeDisposable.add(mNoteViewModel.insert(note))
                finish()
            }
        }
    }

    private fun validateNotesDetails(): Boolean {
        title = mBinding.etTitle.text.toString().trim { it <= ' ' }
        description = mBinding.etDescription.text.toString().trim { it <= ' ' }

        return when {
            TextUtils.isEmpty(title) -> {
                Toast.makeText(this, "Please Enter Title!!", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(description) -> {
                Toast.makeText(this, "Please Enter description!!", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}