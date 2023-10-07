package com.omersungur.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.omersungur.todoapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    fun insertNote(
        noteTitle: String,
        noteContent: String,
        cardBackgroundColor: Int,
        noteTextSize: Float,
        noteTextColor: Int,
        noteCreatedTime: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            noteRepository.insertNote(
                noteTitle,
                noteContent,
                cardBackgroundColor,
                noteTextSize,
                noteTextColor,
                noteCreatedTime
            )
        }
    }

    fun isEntryValid(
        noteTitle: String,
        noteContent: String,
    ): Boolean {
        if (noteTitle.isBlank() || noteContent.isBlank()) {
            return false
        }
        return true
    }
}