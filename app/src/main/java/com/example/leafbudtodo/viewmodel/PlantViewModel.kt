package com.example.leafbudtodo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafbudtodo.model.Plant
import com.example.leafbudtodo.repository.PlantRepository

import kotlinx.coroutines.launch

// ViewModel class to provide data to the UI and handle user interactions for plants
class PlantViewModel(app: Application, private val plantRepository: PlantRepository) : AndroidViewModel(app) {

    // Function to add a plant to the database asynchronously
    fun addPlant(plant: Plant) {
        viewModelScope.launch {
            plantRepository.insertPlant(plant)
        }
    }

    // Function to update a plant in the database asynchronously
    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            plantRepository.updatePlant(plant)
        }
    }

    // Function to delete a plant from the database asynchronously
    fun deletePlant(plant: Plant) {
        viewModelScope.launch {
            plantRepository.deletePlant(plant)
        }
    }

    // Function to get all plants from the database
    fun getAllPlants() = plantRepository.getAllPlants()

    // Function to search for plants in the database
    fun searchPlants(query: String?) = plantRepository.searchPlants(query)
}