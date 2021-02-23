package com.example.studentaid.ui.graduate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentaid.R
import com.example.studentaid.adapters.CoursesAdapter
import com.example.studentaid.data.models.Course
import kotlinx.android.synthetic.main.fragment_course.*

class CourseFragment : Fragment() {
    val lists = dummyCourses()
    val adapter = CoursesAdapter(lists)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false)
    }

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        rv_courses.adapter = adapter
        adapter.notifyDataSetChanged()

    }
    fun dummyCourses():List<Course>{
        val list = mutableListOf<Course>()
        list.add(Course(imageSrc = R.drawable.course,name = "Course Name",desc = "For Law graduates",usefulness = "Usefulness of thus course",time = "6 hrs","720 EG"))
        list.add(Course(imageSrc = R.drawable.course,name = "Course Name",desc = "For Law graduates",usefulness = "Usefulness of thus course",time = "6 hrs","720 EG"))
        list.add(Course(imageSrc = R.drawable.course,name = "Course Name",desc = "For Law graduates",usefulness = "Usefulness of thus course",time = "6 hrs","720 EG"))
        list.add(Course(imageSrc = R.drawable.course,name = "Course Name",desc = "For Law graduates",usefulness = "Usefulness of thus course",time = "6 hrs","720 EG"))
        list.add(Course(imageSrc = R.drawable.course,name = "Course Name",desc = "For Law graduates",usefulness = "Usefulness of thus course",time = "6 hrs","720 EG"))
        list.add(Course(imageSrc = R.drawable.course,name = "Course Name",desc = "For Law graduates",usefulness = "Usefulness of thus course",time = "6 hrs","720 EG"))
        list.add(Course(R.drawable.course1))
        list.add(Course(R.drawable.course2))
        list.add(Course(R.drawable.course))
        list.add(Course(R.drawable.course2))
        list.add(Course(R.drawable.course1))
        return list
    }

}