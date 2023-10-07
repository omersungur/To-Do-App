package com.omersungur.todoapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.snackbar.Snackbar
import com.omersungur.todoapp.data.entity.Note
import com.omersungur.todoapp.databinding.NoteRowBinding
import com.omersungur.todoapp.ui.fragment.NoteListFragmentDirections
import com.omersungur.todoapp.ui.viewmodel.NoteListViewModel

class NoteListRecyclerAdapter(
    private val context: Context,
    private val noteList: List<Note>,
    private val viewModel: NoteListViewModel
) : RecyclerView.Adapter<NoteListRecyclerAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding: NoteRowBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]

        holder.binding.apply {

            tvRowNoteTitle.text = note.noteTitle
            tvRowNoteContent.text = note.noteContent

            cardViewNoteRow.setCardBackgroundColor(note.cardBackgroundColor)

            tvRowNoteContent.textSize = note.noteTextSize

            tvRowNoteTitle.setTextColor(note.noteTextColor)
            tvRowNoteContent.setTextColor(note.noteTextColor)

            tvRowNoteCreatedTime.text = note.noteCreatedTime

            ivDeleteNote.setOnClickListener {
                Snackbar.make(
                    it,
                    "Seçilen Notu Silmek İstiyor Musunuz?",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("EVET") {
                        Toast.makeText(context, "Not Başarıyla Silindi!", Toast.LENGTH_SHORT).show()
                        deleteNote(note.noteId)
                    }.setActionTextColor(0xFF22FF22.toInt())
                    .show()
            }

            cardViewNoteRow.setOnClickListener {

                val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(
                        note.noteId,
                        note.noteTextColor.toString(),
                        note.noteTextSize
                    )
                Navigation.findNavController(it).navigate(action)
            }


        }
    }

    private fun deleteNote(noteId: Int) {
        viewModel.deleteNote(noteId)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}