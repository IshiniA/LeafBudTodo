package com.example.leafbudtodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.leafbudtodo.MainActivity
import com.example.leafbudtodo.R
import com.example.leafbudtodo.adapter.PlantAdapter
import com.example.leafbudtodo.databinding.FragmentHomeBinding
import com.example.leafbudtodo.model.Plant
import com.example.leafbudtodo.viewmodel.PlantViewModel

// Fragment for displaying the list of plants
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var plantsViewModel : PlantViewModel
    private lateinit var plantAdapter: PlantAdapter

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain the MenuHost from the activity to handle menu events
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Obtain the PlantViewModel from the MainActivity
        plantsViewModel = (activity as MainActivity).plantViewModel
        // Set up the RecyclerView for displaying plants
        setupHomeRecyclerView()

        // Set a click listener for the FAB to navigate to the AddPlantFragment
        binding.addPlantFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addPlantFragment)
        }
    }

    // Update the UI based on the list of plants
    private fun updateUI(plant: List<Plant>?){
        if (plant != null){
            if (plant.isNotEmpty()){
                binding.emptyPlantImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            } else {
                binding.emptyPlantImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    // Set up the RecyclerView for displaying plants
    private fun setupHomeRecyclerView(){
        plantAdapter = PlantAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = plantAdapter
        }

        // Observe changes in the list of plants and update the RecyclerView accordingly
        activity?.let {
            plantsViewModel.getAllPlants().observe(viewLifecycleOwner) { note ->
                plantAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    // Function to search for plants based on the query text
    private fun searchPlant(query: String){
        val searchQuery = "%$query"
        plantsViewModel.searchPlants(searchQuery).observe(this) { list ->
            plantAdapter.differ.submitList(list)
        }
    }

    // Handle query submission event
    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    // Handle text change event in the search view
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchPlant(newText)
        }
        return true
    }

    // Clean up the binding instance when the fragment is destroyed
    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    // Create the menu for the HomeFragment
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        // Set up the SearchView in the menu
        val menuSearch = menu.findItem(R.id.searchMenu).actionView as androidx.appcompat.widget.SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    // Handle menu item clicks
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }


}