package com.example.studentsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.databinding.ActivityNewStudentBinding
import com.example.studentsapp.models.Model
import com.example.studentsapp.models.Student

class NewStudentActivity : AppCompatActivity() {

    var binding: ActivityNewStudentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityNewStudentBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupToolbar()
        setupButtons()

        // Set profile picture
        binding?.studentImageView?.setImageResource(R.drawable.profile_picture_student)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupButtons() {
        binding?.saveButton?.setOnClickListener {
            saveStudent()
        }

        binding?.cancelButton?.setOnClickListener {
            finish()
        }
    }

    private fun saveStudent() {
        val name = binding?.nameEditText?.text.toString().trim()
        val id = binding?.idEditText?.text.toString().trim()
        val phone = binding?.phoneEditText?.text.toString().trim()
        val address = binding?.addressEditText?.text.toString().trim()
        val isChecked = binding?.checkedCheckBox?.isChecked ?: false

        // Validation
        if (name.isEmpty()) {
            binding?.nameEditText?.error = getString(R.string.error_empty_name)
            return
        }

        if (id.isEmpty()) {
            binding?.idEditText?.error = getString(R.string.error_empty_id)
            return
        }

        // Check for duplicate ID
        if (Model.shared.students.any { it.id == id }) {
            binding?.idEditText?.error = getString(R.string.error_duplicate_id)
            return
        }

        // Create and save student
        val student = Student(
            name = name,
            id = id,
            phone = phone,
            address = address,
            isChecked = isChecked,
            profilePicture = R.drawable.profile_picture_student
        )

        Model.shared.students.add(student)

        Toast.makeText(this, R.string.student_added, Toast.LENGTH_SHORT).show()
        finish()
    }
}
