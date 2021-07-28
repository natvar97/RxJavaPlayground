package com.indialone.rxjavaplayground.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indialone.rxjavaplayground.databinding.NoteItemLayoutBinding

class NotesRvAdapter(
    private val notes: ArrayList<NoteEntity>
) : RecyclerView.Adapter<NotesRvAdapter.NotesRvViewHolder>() {
    class NotesRvViewHolder(itemView: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val tvTitle = itemView.tvTitle
        private val tvDescription = itemView.tvDescription

        fun bind(note: NoteEntity) {
            tvTitle.text = note.title
            tvDescription.text = note.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesRvViewHolder {
        val view = NoteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesRvViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesRvViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}