package com.example.studentsapp.models

import com.example.studentsapp.R

class Model private constructor() {
    val students = mutableListOf<Student>()

    init {
        // Initialize with sample students for testing RecyclerView scrolling
        val firstNames = listOf("Emma", "Liam", "Olivia", "Noah", "Ava", "Ethan", "Sophia", "Mason", "Isabella", "William")
        val lastNames = listOf("Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez")
        val streets = listOf("Main St", "Oak Ave", "Maple Dr", "Cedar Ln", "Pine Rd", "Elm St", "Park Ave", "Lake Dr", "Hill Rd", "River Way")

        for (i in 1..10) {
            val firstName = firstNames[i - 1]
            val lastName = lastNames[i - 1]
            students.add(
                Student(
                    name = "$firstName $lastName",
                    id = "ID${1000 + i}",
                    phone = "555-${String.format("%04d", 1000 + i)}",
                    address = "${(i * 10) + 100} ${streets[i - 1]}",
                    isChecked = false,
                    profilePicture = R.drawable.profile_picture_student
                )
            )
        }
    }

    companion object {
        val shared = Model()
    }
}
