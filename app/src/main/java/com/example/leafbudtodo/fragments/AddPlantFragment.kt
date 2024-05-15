package com.example.leafbudtodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.leafbudtodo.MainActivity
import com.example.leafbudtodo.R
import com.example.leafbudtodo.databinding.FragmentAddPlantBinding
import com.example.leafbudtodo.model.Plant
import com.example.leafbudtodo.viewmodel.PlantViewModel

// Fragment to add a new plant to the list
class AddPlantFragment : Fragment(R.layout.fragment_add_plant), MenuProvider {

    private var addPlantBinding: FragmentAddPlantBinding? = null
    private val binding get() = addPlantBinding!!

    private lateinit var plantViewModel : PlantViewModel
    private lateinit var addPlantView: View

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addPlantBinding = FragmentAddPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain the MenuHost from the activity to handle menu events
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Obtain the PlantViewModel from the MainActivity
        plantViewModel = (activity as MainActivity).plantViewModel
        addPlantView = view
    }

    // Function to save the plant to the database
    private fun savePlant(view: View){
        val plantTitle = binding.addPlantTitle.text.toString().trim()
        val plantDesc = binding.addPlantDesc.text.toString().trim()

        // Check if the plant title is not empty
        if (plantTitle.isNotEmpty()){
            // Create a new plant object
            val plant = Plant(
                0,
                plantTitle,
                plantDesc
            )
            // Add the plant to the ViewModel
            plantViewModel.addPlant(plant)

            // Display a toast message indicating the plant has been added
            Toast.makeText(addPlantView.context, "Plant added to the list", Toast.LENGTH_SHORT).show()
            // Navigate back to the home fragment
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            // Display a toast message indicating the user should enter a plant name
            Toast.makeText(addPlantView.context, "Please enter a plant name", Toast.LENGTH_SHORT).show()
        }
    }

    // Override to create the menu for adding a plant
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_plant, menu)
    }

    // Override to handle menu item clicks
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                savePlant(addPlantView)
                true
            }
            else -> false
        }
    }

    // Clean up the binding instance when the fragment is destroyed
    override fun onDestroy() {
        super.onDestroy()
        addPlantBinding = null
    }


}