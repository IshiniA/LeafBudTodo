package com.example.leafbudtodo.fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.example.leafbudtodo.MainActivity
import com.example.leafbudtodo.R
import com.example.leafbudtodo.databinding.FragmentEditPlantBinding
import com.example.leafbudtodo.model.Plant
import com.example.leafbudtodo.viewmodel.PlantViewModel

// Fragment to edit an existing plant
class EditPlantFragment : Fragment(R.layout.fragment_edit_plant), MenuProvider {

    private var editPlantBinding: FragmentEditPlantBinding? = null
    private val binding get() = editPlantBinding!!

    private lateinit var plantsViewModel: PlantViewModel
    private lateinit var currentPlant: Plant

    // Obtain navigation arguments for the current plant
    private val args: EditPlantFragmentArgs by navArgs()
    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editPlantBinding = FragmentEditPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain the MenuHost from the activity to handle menu events
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Obtain the PlantViewModel from the MainActivity
        plantsViewModel = (activity as MainActivity).plantViewModel
        // Get the current plant from the navigation arguments
        currentPlant = args.note!!


        binding.editPlantTitle.setText(currentPlant.title)
        binding.editPlantDesc.setText(currentPlant.description)

        // Set a click listener for the FAB to update the plant
        binding.editPlantFab.setOnClickListener {
            val plantTitle = binding.editPlantTitle.text.toString().trim()
            val plantDesc = binding.editPlantDesc.text.toString().trim()

            // Check if the plant title is not empty
            if (plantTitle.isNotEmpty()) {
                // Create a new plant object with updated details
                val plant = Plant(
                    currentPlant.id,
                    plantTitle,
                    plantDesc
                )
                // Update the plant in the ViewModel
                plantsViewModel.updatePlant(plant)
                // Navigate back to the home fragment
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                // Display a toast message indicating the user should enter a plant name
                Toast.makeText(context, "Please enter a plant name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to delete the current plant
    private fun deleteNote(){
        // Display a confirmation dialog for deleting the plant
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Plant")
            setMessage("Are you sure you want to delete this plant from the list?")
            setPositiveButton("Yes"){ _, _ ->
                // Delete the plant from the ViewModel
                plantsViewModel.deletePlant(currentPlant)
                // Display a toast message indicating the plant has been deleted
                Toast.makeText(context, "Plant added to the list", Toast.LENGTH_SHORT).show()
                // Navigate back to the home fragment
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)}
            setNegativeButton("No", null)
        }.create().show()

    }

    // Override to create the menu for editing a plant
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_plant, menu)
    }

    // Override to handle menu item clicks
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }

    // Clean up the binding instance when the fragment is destroyed
    override fun onDestroy() {
        super.onDestroy()
        editPlantBinding = null
    }


}