package com.example.studentdetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val rollNumber: String,
    val age: Int,
    var deleted: Boolean = false // New field to mark student as deleted
)

