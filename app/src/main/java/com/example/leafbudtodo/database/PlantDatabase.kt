package com.example.leafbudtodo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leafbudtodo.model.Plant


// Database class representing the Room database for the application
@Database(entities = [Plant::class], version = 1)
abstract class PlantDatabase: RoomDatabase() {

    // Abstract method to retrieve the PlantDao interface for interacting with the database
    abstract fun getPlantDao(): PlantDao

    // Companion object to provide methods for creating or accessing the database instance
    companion object{
        @Volatile //changes made in one thread are visible to all other threads immediately
        private var instance: PlantDatabase? = null //singleton instance of the database or null
        private val LOCK = Any() //synchronize the database creation process

        // Operator function to create or access the database instance
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            createDatabase(context).also { instance = it }
        }

        // Function to create the database instance
        private fun createDatabase(context: Context) =
            Room.databaseBuilder( //create a database
                context.applicationContext,
                PlantDatabase::class.java, //database class
                "plant_db" //database name
            ).build()

    }

}