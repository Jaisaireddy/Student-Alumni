package com.example.studentdetails

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StudentEntity::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        private var instance: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student-db"
                ).build()
            }
            return instance as StudentDatabase
        }
    }
}
