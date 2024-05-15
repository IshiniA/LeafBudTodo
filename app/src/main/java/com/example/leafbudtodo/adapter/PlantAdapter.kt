package com.example.leafbudtodo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.leafbudtodo.model.Plant
import com.example.leafbudtodo.databinding.PlantLayoutBinding
import com.example.leafbudtodo.fragments.HomeFragmentDirections

// Adapter class for the RecyclerView
class PlantAdapter : RecyclerView.Adapter<PlantAdapter.NoteViewHolder>() {

    // ViewHolder class for the RecyclerView items
    class NoteViewHolder(val itemBinding: PlantLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // Callback for calculating the diff between two non-null items in a list.
    private val differCallback = object : DiffUtil.ItemCallback<Plant>() {
        // check whether the item represents a note or not
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return  oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description
        }

        // check whether the content of the item is the same or not
        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
    }
    // AsyncListDiffer to calculate the differences between the old list and the new list.
    val differ = AsyncListDiffer(this, differCallback)

    // Create new ViewHolders (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            PlantLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentP = differ.currentList[position]

        // Bind data to the ViewHolder
        holder.itemBinding.plantTitle.text = currentP.title
        holder.itemBinding.plantDesc.text = currentP.description

        // Set click listener to navigate to the edit screen
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentP)
            it.findNavController().navigate(action)
        }
    }
}