package com.example.studentdetails

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            saveStudentDetails()
        }
    }

    private fun saveStudentDetails() {
        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val rollNumberEditText: EditText = findViewById(R.id.rollNumberEditText)
        val ageEditText: EditText = findViewById(R.id.ageEditText)

        val name = nameEditText.text.toString()
        val rollNumber = rollNumberEditText.text.toString()
        val age = ageEditText.text.toString().toInt()

        val student = Student(name, rollNumber, age)

        lifecycleScope.launch {
            saveStudentToDatabase(applicationContext, student)
        }
    }

    private suspend fun saveStudentToDatabase(context: Context, student: Student) {
        withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context,
                StudentDatabase::class.java, "student-db"
            ).build()

            val studentEntity = StudentEntity(
                name = student.name,
                rollNumber = student.rollNumber,
                age = student.age
            )

            db.studentDao().insertStudent(studentEntity)
        }
    }
}
