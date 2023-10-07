package com.omersungur.todoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omersungur.todoapp.data.entity.Note

@Database(entities = [Note::class], version = 5)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao
}