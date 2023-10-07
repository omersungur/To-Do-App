package com.omersungur.todoapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @ColumnInfo(name = "note_id") @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    @ColumnInfo(name = "note_title") val noteTitle: String,
    @ColumnInfo(name = "note_content") val noteContent: String,
    @ColumnInfo(name = "note_background_color") val cardBackgroundColor: Int,
    @ColumnInfo(name = "note_text_size") val noteTextSize: Float,
    @ColumnInfo(name = "note_text_color") val noteTextColor: Int,
    @ColumnInfo(name = "created_time") val noteCreatedTime: String
)
