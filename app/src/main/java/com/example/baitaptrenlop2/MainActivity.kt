package com.example.baitaptrenlop2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var searchEditText: EditText
    private lateinit var studentAdapter: ArrayAdapter<Student>
    private var studentList = mutableListOf<Student>()
    private var filteredList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo danh sách sinh viên
        studentList.add(Student("Nguyen Van A", "123456"))
        studentList.add(Student("Tran Thi B", "654321"))
        studentList.add(Student("Le Van C", "112233"))
        // Thêm sinh viên khác...

        listView = findViewById(R.id.listView)
        searchEditText = findViewById(R.id.searchEditText)

        // Khởi tạo ArrayAdapter với layout item_student
        studentAdapter = object : ArrayAdapter<Student>(this, R.layout.item_student, filteredList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val nameTextView: TextView = view.findViewById(R.id.nameTextView)
                val mssvTextView: TextView = view.findViewById(R.id.mssvTextView)
                val student = getItem(position)
                nameTextView.text = student?.name
                mssvTextView.text = student?.mssv
                return view
            }
        }

        listView.adapter = studentAdapter
        filteredList.addAll(studentList) // Hiển thị toàn bộ danh sách ban đầu
        studentAdapter.notifyDataSetChanged()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filter(text: String) {
        filteredList.clear()
        if (text.length > 2) {
            for (student in studentList) {
                if (student.name.contains(text, true) || student.mssv.contains(text, true)) {
                    filteredList.add(student)
                }
            }
        } else {
            filteredList.addAll(studentList) // Hiển thị toàn bộ danh sách
        }
        studentAdapter.notifyDataSetChanged()
    }
}
