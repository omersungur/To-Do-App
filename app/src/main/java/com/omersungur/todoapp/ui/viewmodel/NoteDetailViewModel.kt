package com.omersungur.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.todoapp.data.entity.Note
import com.omersungur.todoapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    fun updateNote(
        noteId: Int,
        noteTitle: String,
        noteContent: String,
        cardBackgroundColor: Int,
        noteTextSize: Float,
        noteTextColor: Int,
        noteCreatedTime: String
    ) {
        viewModelScope.launch {
            repository.updateNote(
                noteId,
                noteTitle,
                noteContent,
                cardBackgroundColor,
                noteTextSize,
                noteTextColor,
                noteCreatedTime
            )
        }
    }

    fun getNoteById(noteId: Int): Flow<Note> {
       return repository.getNoteById(noteId)
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