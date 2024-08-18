package com.example.studentdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private var students: MutableList<Student>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(student: Student)
    }

    class StudentViewHolder(itemView: View, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.studentNameTextView)
        private val rollNumberTextView: TextView =
            itemView.findViewById(R.id.studentRollNumberTextView)
        private val ageTextView: TextView = itemView.findViewById(R.id.studentAgeTextView)

        fun bind(student: Student) {
            nameTextView.text = student.name
            rollNumberTextView.text = student.rollNumber
            ageTextView.text = student.age.toString()

            itemView.setOnClickListener {
                listener.onItemClick(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun updateData(newList: MutableList<Student>) {
        students.clear()
        students.addAll(newList)
        notifyDataSetChanged()
    }

    fun deleteStudent(student: Student) {
        students.remove(student)
        notifyDataSetChanged()
    }
}
