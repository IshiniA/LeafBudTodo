package com.example.leafbudtodo.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leafbudtodo.repository.PlantRepository

// ViewModelFactory class to create instances of PlantViewModel
class PlantViewModelFactory(val app: Application, private val plantRepository: PlantRepository) : ViewModelProvider.Factory{
    // Function to create an instance of PlantViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlantViewModel(app, plantRepository) as T
    }


}