package com.omersungur.todoapp.data.datasource

import com.omersungur.todoapp.data.entity.Note
import com.omersungur.todoapp.room.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteDataSource(private val noteDao: NoteDao) {

    suspend fun insertNote(
        noteTitle: String,
        noteContent: String,
        cardBackgroundColor: Int,
        noteTextSize: Float,
        noteTextColor: Int,
        noteCreatedTime: String
    ) {
        val note = Note(0, noteTitle, noteContent, cardBackgroundColor, noteTextSize, noteTextColor,noteCreatedTime)
        noteDao.insertNote(note = note)
    }

    suspend fun updateNote(
        noteId: Int,
        noteTitle: String,
        noteContent: String,
        cardBackgroundColor: Int,
        noteTextSize: Float,
        noteTextColor: Int,
        noteCreatedTime: String
    ) {
        val note =
            Note(noteId, noteTitle, noteContent, cardBackgroundColor, noteTextSize, noteTextColor,noteCreatedTime)
        noteDao.updateNote(note = note)
    }

    suspend fun deleteNote(
        noteId: Int,
    ) {
        val note =
            Note(noteId,"","",0,0f,0,"")
        noteDao.deleteNote(note = note)
    }

    fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    fun getNoteById(noteId: Int): Flow<Note> {
        return noteDao.getNoteById(noteId)
    }

    suspend fun searchNotes(query: String): List<Note> = withContext(Dispatchers.Default) {
        return@withContext noteDao.searchNotes(query)
    }
}

// insert içine id eklemesi denenecek!