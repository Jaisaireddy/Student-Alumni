package com.example.studentdetails

import androidx.room.*

@Dao
interface StudentDao {

    @Query("SELECT * FROM students WHERE name LIKE :query OR rollNumber LIKE :query")
    fun getStudentsByQuery(query: String): List<StudentEntity>

    @Query("SELECT * FROM students WHERE name LIKE :query AND deleted = 0")
    suspend fun getNonDeletedStudentsByQuery(query: String): List<StudentEntity>


    @Query("SELECT * FROM students")
    fun getAllStudents(): List<StudentEntity>

    @Query("SELECT * FROM students WHERE deleted = 0")
    suspend fun getNonDeletedStudents(): List<StudentEntity>

    @Insert
    fun insertStudent(student: StudentEntity)

    @Update
    suspend fun updateStudent(student: StudentEntity)

    @Delete
    suspend fun deleteStudent(student: StudentEntity) {
        student.deleted = true
        updateStudent(student)
    }
}

