package com.example.leafbudtodo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.leafbudtodo.model.Plant

// Data Access Object (DAO) interface for interacting with the Plant entity in the Room database
@Dao
interface PlantDao {

    // Insert a plant into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE) //if there is a conflict, replace the existing row
    suspend fun insertPlant(plant: Plant) //suspend function is a function that call from a couroutine

    // Update an existing plant in the database
    @Update
    suspend fun updatePlant(plant: Plant)

    // Delete a plant from the database
    @Delete
    suspend fun deletePlant(plant: Plant)

    // Retrieve all plants from the database, ordered by ID in descending order
    @Query("SELECT * FROM PLANTS ORDER BY id DESC")
    fun getAllPlants(): LiveData<List<Plant>>

    // Search for plants in the database based on a query string matching the title or description
    @Query("SELECT * FROM PLANTS WHERE title LIKE :query OR description LIKE :query")
    fun searchPlants(query: String?): LiveData<List<Plant>>
}