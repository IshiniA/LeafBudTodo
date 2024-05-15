package com.example.leafbudtodo.repository

import com.example.leafbudtodo.database.PlantDatabase
import com.example.leafbudtodo.model.Plant

// Repository class to abstract access to data sources for plants
class PlantRepository(private val db: PlantDatabase) {

    // Function to insert a plant into the database asynchronously
    suspend fun insertPlant(plant: Plant) = db.getPlantDao().insertPlant(plant)
    // Function to delete a plant from the database asynchronously
    suspend fun deletePlant(plant: Plant) = db.getPlantDao().deletePlant(plant)
    // Function to update a plant in the database asynchronously
    suspend fun updatePlant(plant: Plant) = db.getPlantDao().updatePlant(plant)

    // Function to get all plants from the database
    fun getAllPlants() = db.getPlantDao().getAllPlants()
    // Function to search for plants in the database
    fun searchPlants(query: String?) = db.getPlantDao().searchPlants(query)

}