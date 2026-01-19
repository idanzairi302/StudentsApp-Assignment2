package com.example.studentsapp

import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.databinding.ItemStudentBinding
import com.example.studentsapp.models.Student

class StudentRowViewHolder(
    private val binding: ItemStudentBinding,
    private val onStudentClick: (Student) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    private var student: Student? = null

    init {
        // Set up click listener for checkbox in init block
        binding.studentCheckBox.setOnClickListener { view ->
            (view?.tag as? Int)?.let { tag ->
                student?.isChecked = binding.studentCheckBox.isChecked
            }
        }

        // Set up click listener for entire row
        binding.root.setOnClickListener {
            student?.let { onStudentClick(it) }
        }
    }

    fun bind(student: Student, position: Int) {
        this.student = student
        binding.studentImageView.setImageResource(student.profilePicture)
        binding.studentNameTextView.text = student.name
        binding.studentIdTextView.text = student.id
        binding.studentCheckBox.apply {
            isChecked = student.isChecked
            tag = position
        }
    }
}
