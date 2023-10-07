package com.omersungur.todoapp.data.repository

import com.omersungur.todoapp.data.datasource.NoteDataSource
import com.omersungur.todoapp.data.entity.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDataSource: NoteDataSource) {

    suspend fun insertNote(
        noteTitle: String,
        noteContent: String,
        cardBackgroundColor: Int,
        noteTextSize: Float,
        noteTextColor: Int,
        noteCreatedTime: String
    ) = noteDataSource.insertNote(
        noteTitle,
        noteContent,
        cardBackgroundColor,
        noteTextSize,
        noteTextColor,
        noteCreatedTime
    )

    suspend fun updateNote(
        noteId: Int,
        noteTitle: String,
        noteContent: String,
        cardBackgroundColor: Int,
        noteTextSize: Float,
        noteTextColor: Int,
        noteCreatedTime: String
    ) = noteDataSource.updateNote(
        noteId,
        noteTitle,
        noteContent,
        cardBackgroundColor,
        noteTextSize,
        noteTextColor,
        noteCreatedTime
    )

    suspend fun deleteNote(noteId: Int) = noteDataSource.deleteNote(noteId)

    fun getNotes(): Flow<List<Note>> = noteDataSource.getNotes()
    fun getNoteById(noteId: Int): Flow<Note> = noteDataSource.getNoteById(noteId)
    suspend fun searchNotes(query: String): List<Note> = noteDataSource.searchNotes(query)
}

// fonksiyon dönüş tipleri kaldırılacak.