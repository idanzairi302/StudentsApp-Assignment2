package com.example.studentsapp.models

data class Student(
    val name: String,
    val id: String,
    var phone: String,
    var address: String,
    var isChecked: Boolean,
    val profilePicture: Int
)
