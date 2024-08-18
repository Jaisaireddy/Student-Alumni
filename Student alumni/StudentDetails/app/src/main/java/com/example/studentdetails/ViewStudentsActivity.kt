package com.example.studentdetails

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewStudentsActivity : AppCompatActivity(), StudentAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText

    private lateinit var studentDao: StudentDao
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students)

        // Initialize views
        recyclerView = findViewById(R.id.studentsRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)

        // Get studentDao instance
        studentDao = StudentDatabase.getInstance(this).studentDao()

        // Set up recycler view
        adapter = StudentAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load data from database
        loadData()

        // Add search functionality
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterStudents(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun loadData() {
        GlobalScope.launch {
            val students = studentDao.getNonDeletedStudents()
            runOnUiThread {
                adapter.updateData(students.map { studentEntity ->
                    Student(
                        studentEntity.name,
                        studentEntity.rollNumber,
                        studentEntity.age
                    )
                }.toMutableList())
            }
        }
    }

    private fun filterStudents(query: String) {
        GlobalScope.launch {
            val filteredStudents = studentDao.getNonDeletedStudentsByQuery("%$query%")
            runOnUiThread {
                adapter.updateData(filteredStudents.map { studentEntity ->
                    Student(
                        studentEntity.name,
                        studentEntity.rollNumber,
                        studentEntity.age
                    )
                }.toMutableList())
            }
        }
    }


    override fun onItemClick(student: Student) {
        // Show alert dialog to confirm delete
        AlertDialog.Builder(this)
            .setTitle("Delete student")
            .setMessage("Are you sure you want to delete ${student.name}?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Delete student from database and update adapter
                GlobalScope.launch {
                    studentDao.deleteStudent(StudentEntity(name = student.name, rollNumber = student.rollNumber, age = student.age))
                    runOnUiThread {
                        adapter.deleteStudent(student)
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
