package com.example.studentsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.databinding.ActivityStudentDetailsBinding
import com.example.studentsapp.models.Model

class StudentDetailsActivity : AppCompatActivity() {

    var binding: ActivityStudentDetailsBinding? = null
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
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

        setupToolbar()
        displayStudentDetails()
    }

    override fun onResume() {
        super.onResume()
        // Refresh in case student was edited
        displayStudentDetails()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun displayStudentDetails() {
        val student = Model.shared.students.find { it.id == studentId }

        if (student == null) {
            // Student was deleted, go back
            finish()
            return
        }

        binding?.apply {
            studentImageView.setImageResource(student.profilePicture)
            nameTextView.text = student.name
            idTextView.text = student.id
            phoneTextView.text = student.phone
            addressTextView.text = student.address
            checkedCheckBox.isChecked = student.isChecked
        }
    }
}
