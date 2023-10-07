package com.omersungur.todoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.omersungur.todoapp.data.entity.Note
import com.omersungur.todoapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val notes: LiveData<List<Note>> = noteRepository.getNotes().asLiveData()
    val newList = MutableLiveData<List<Note>>()

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }

    fun searchNote(query: String) {
        viewModelScope.launch {
            try {
                newList.value = noteRepository.searchNotes(query)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
