package com.example.leafbudtodo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leafbudtodo.model.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object{
        @Volatile //changes made in one thread are visible to all other threads immediately
        private var instance: NoteDatabase? = null //singleton instance of the database or null
        private val LOCK = Any() //synchronize the database creation process

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder( //create a database
                context.applicationContext,
                NoteDatabase::class.java, //database class
                "note_db" //database name
            ).build()

    }

}