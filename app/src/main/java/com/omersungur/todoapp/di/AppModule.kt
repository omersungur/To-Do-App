package com.omersungur.todoapp.di

import android.content.Context
import androidx.room.Room
import com.omersungur.todoapp.data.datasource.NoteDataSource
import com.omersungur.todoapp.data.repository.NoteRepository
import com.omersungur.todoapp.room.NoteDao
import com.omersungur.todoapp.room.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(noteDataSource: NoteDataSource): NoteRepository {
        return NoteRepository(noteDataSource)
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(noteDao: NoteDao): NoteDataSource {
        return NoteDataSource(noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteDao(@ApplicationContext context: Context): NoteDao {
        val vt = Room.databaseBuilder(context,NoteDatabase::class.java,"note_database.db")
            .fallbackToDestructiveMigration() // Database sürümü artınca veri tabanının temizlenmesi için.
            .build()
        return vt.getNoteDao()
    }
}