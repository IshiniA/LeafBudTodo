package com.example.leafbudtodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.leafbudtodo.database.PlantDatabase
import com.example.leafbudtodo.repository.PlantRepository
import com.example.leafbudtodo.viewmodel.PlantViewModel
import com.example.leafbudtodo.viewmodel.PlantViewModelFactory

// Main activity class
class MainActivity : AppCompatActivity() {

    // Variable to hold the PlantViewModel
    lateinit var plantViewModel: PlantViewModel

    // Function to create the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    // Function to setup the PlantViewModel
    private fun setupViewModel() {
        val plantRepository = PlantRepository(PlantDatabase(this))
        val viewModelProviderFactory = PlantViewModelFactory(application, plantRepository)
        plantViewModel = ViewModelProvider(this, viewModelProviderFactory)[PlantViewModel::class.java]
    }
}