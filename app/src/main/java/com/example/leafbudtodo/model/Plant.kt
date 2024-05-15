package com.example.leafbudtodo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Entity annotation defines this class as a database entity
@Entity(tableName = "plants")
@Parcelize // converts complex objects into a format that can be easily transferred between activities/fragments
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,

    ): Parcelable // Implementing Parcelable interface allows the object to be passed between components using Intent extras or Bundle arguments

