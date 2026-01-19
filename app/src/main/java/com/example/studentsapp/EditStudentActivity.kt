package com.example.studentsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.databinding.ActivityEditStudentBinding
import com.example.studentsapp.models.Model
import com.example.studentsapp.models.Student

class EditStudentActivity : AppCompatActivity() {

    var binding: ActivityEditStudentBinding? = null
    private var studentId: String? = null
    private var originalStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get student ID from intent
        studentId = intent.getStringExtra("STUDENT_ID")
        if (studentId == null) {
            finish()
            return
        }

        // Load student data
        originalStudent = Model.shared.students.find { it.id == studentId }
        if (originalStudent == null) {
            finish()
            return
        }

        setupToolbar()
        populateFields()
        setupButtons()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun populateFields() {
        originalStudent?.let { student ->
            binding?.apply {
                studentImageView.setImageResource(student.profilePicture)
                nameEditText.setText(student.name)
                idEditText.setText(student.id)
                phoneEditText.setText(student.phone)
                addressEditText.setText(student.address)
                checkedCheckBox.isChecked = student.isChecked
            }
        }
    }

    private fun setupButtons() {
        binding?.saveButton?.setOnClickListener {
            saveStudent()
        }

        binding?.cancelButton?.setOnClickListener {
            finish()
        }

        binding?.deleteButton?.setOnClickListener {
            confirmDelete()
        }
    }

    private fun saveStudent() {
        val name = binding?.nameEditText?.text.toString().trim()
        val newId = binding?.idEditText?.text.toString().trim()
        val phone = binding?.phoneEditText?.text.toString().trim()
        val address = binding?.addressEditText?.text.toString().trim()
        val isChecked = binding?.checkedCheckBox?.isChecked ?: false

        // Validation
        if (name.isEmpty()) {
            binding?.nameEditText?.error = getString(R.string.error_empty_name)
            return
        }

        if (newId.isEmpty()) {
            binding?.idEditText?.error = getString(R.string.error_empty_id)
            return
        }

        // Check for duplicate ID (if ID was changed)
        if (newId != studentId && Model.shared.students.any { it.id == newId }) {
            binding?.idEditText?.error = getString(R.string.error_duplicate_id)
            return
        }

        // Find the student index
        val index = Model.shared.students.indexOfFirst { it.id == studentId }
        if (index != -1) {
            // Update the student in the list
            Model.shared.students[index] = Student(
                name = name,
                id = newId,
                phone = phone,
                address = address,
                isChecked = isChecked,
                profilePicture = originalStudent!!.profilePicture
            )

            Toast.makeText(this, R.string.student_updated, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_confirmation_title)
            .setMessage(getString(R.string.delete_confirmation_message))
            .setPositiveButton(R.string.delete_confirm) { _, _ ->
                deleteStudent()
            }
            .setNegativeButton(R.string.cancel_button, null)
            .show()
    }

    private fun deleteStudent() {
        Model.shared.students.removeIf { it.id == studentId }
        Toast.makeText(this, R.string.student_deleted, Toast.LENGTH_SHORT).show()
        finish()
    }
}
