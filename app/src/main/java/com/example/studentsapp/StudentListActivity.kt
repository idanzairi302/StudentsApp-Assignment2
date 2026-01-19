package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.databinding.ActivityStudentListBinding
import com.example.studentsapp.models.Model

class StudentListActivity : AppCompatActivity() {

    var binding: ActivityStudentListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStudentListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupFab()
    }

    override fun onResume() {
        super.onResume()
        // Refresh list when returning from other screens
        binding?.studentsRecyclerView?.adapter = StudentsAdapter(Model.shared.students) { student ->
            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra("STUDENT_ID", student.id)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val layout = LinearLayoutManager(this)
        binding?.studentsRecyclerView?.layoutManager = layout
        binding?.studentsRecyclerView?.setHasFixedSize(true)
    }

    private fun setupFab() {
        binding?.addStudentFab?.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }
    }
}
