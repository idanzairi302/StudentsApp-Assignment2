package com.example.studentsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.databinding.ItemStudentBinding
import com.example.studentsapp.models.Student

class StudentsAdapter(
    private var students: List<Student>,
    private val onStudentClick: (Student) -> Unit
): RecyclerView.Adapter<StudentRowViewHolder>() {

    override fun getItemCount(): Int = students.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentRowViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = ItemStudentBinding.inflate(inflator, parent, false)
        return StudentRowViewHolder(binding = binding, onStudentClick = onStudentClick)
    }

    override fun onBindViewHolder(holder: StudentRowViewHolder, position: Int) {
        holder.bind(students[position], position)
    }
}
