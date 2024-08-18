package com.example.studentdetails



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // set listener on bottom navigation view items
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_add_student -> {
                    // open AddStudentActivity
                    val addStudentIntent = Intent(this, AddStudentActivity::class.java)
                    startActivity(addStudentIntent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_view_students -> {
                    // open ViewStudentsActivity
                    val viewStudentsIntent = Intent(this, ViewStudentsActivity::class.java)
                    startActivity(viewStudentsIntent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
}


