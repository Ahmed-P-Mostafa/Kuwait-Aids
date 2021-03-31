package com.example.studentaid.ui.graduate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.studentaid.R
import com.example.studentaid.adapters.CoursesAdapter
import com.example.studentaid.data.models.Course
import com.example.studentaid.data.models.ProfessionalDegree
import kotlinx.android.synthetic.main.fragment_course.*

class CourseFragment : Fragment() {
    val adapter = CoursesAdapter(null)

    private val ITJobsList = mutableListOf<Course>()
    private val engineeringJobsList = mutableListOf<Course>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false)
    }

    override fun onStart() {
        super.onStart()
        dummyCourses()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        rv_courses.adapter = adapter
        adapter.notifyDataSetChanged()
        val degreeItems = listOf("Information and Technologies", "Mechanical Engineering")

        val degreeAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_menu,degreeItems)
        et_degree.setAdapter(degreeAdapter)
        et_degree.setOnItemClickListener { parent, view, position, id ->
            updateDegrees(degreeItems[position])
        }
        adapter.setOnApplyClickListener(object : CoursesAdapter.OnApplyClickButton {
            override fun OnIClickListener(position: Int, message: String) {
                Toast.makeText(requireContext(),"you applied to $message course",Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }

        })

    }
    fun dummyCourses(){
        ITJobsList.add(Course(name = "Java Programming Language",time = "40 hrs",desc = "Programming by Java – Create Android Application",usefulness = "Programmers "))
        ITJobsList.add(Course(name = "A+",time = "20 Hours",desc = "Repair hardware parts – Add and remove hardware parts",usefulness = "Technical Support"))

        engineeringJobsList.add(Course(name="Power Electronics",time = "60 hrs",desc = "Linking electronic parts together",usefulness = "Engineering "))
        engineeringJobsList.add(Course(name="CAD & Digital Manufacture ",time = "40 hrs",desc = "Create structure schema – Determine shapes and measurements of gears ",usefulness = "AutoCAD Designer"))

    }
    private fun updateDegrees(degree: String) {
        adapter.changeData(getAppropriateJobs(degree))


    }
    private fun getAppropriateJobs(universityDegree:String):MutableList<Course> {
        return if (universityDegree=="Information and Technologies"){
            ITJobsList
        }else if(universityDegree=="Mechanical Engineering"){
            engineeringJobsList
        }else {
            ITJobsList
        }

    }

}