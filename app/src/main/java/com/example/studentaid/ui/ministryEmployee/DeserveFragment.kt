package com.example.studentaid.ui.ministryEmployee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.studentaid.R
import com.example.studentaid.adapters.RequestsAdapter
import com.example.studentaid.base.BaseFragment
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.EmployeeDAO
import com.example.studentaid.utils.Constants
import kotlinx.android.synthetic.main.fragment_deserve.*

class DeserveFragment : BaseFragment() {

    private val TAG = "SuspendedFragment"
    val adapter = RequestsAdapter(null)
    val studentList = mutableListOf<Student>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deserve, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: ")
        showLoader("Loading Requests...",requireContext())
        rv_deserve.adapter = adapter

        adapter.SetOnRequestClickListener(object : RequestsAdapter.OnRequestClickListener {
            override fun onRequestClickListener(student: Student) {
                val action = DeserveFragmentDirections.actionDeserveFragmentToDetailsFragment(student)
                findNavController().navigate(action)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        getPendingRequests()
    }
    private fun getPendingRequests(){
        EmployeeDAO.getAllStudentsRequests({
            studentList.clear()
            it.documents.forEach {
                val student = it.toObject(Student::class.java)
                if (student != null && student.condition == Constants.CONDITION_DESERVED) {
                    Log.d(TAG, "getPendingRequests: ${student.condition}")
                    studentList.add(student)
                }
            }
            if (studentList.size==0){
                iv_deserveEmptyList.visibility = View.VISIBLE
            }
            adapter.changeData(studentList)

        }, {
            showMessage(it.localizedMessage,requireContext())

        })
        hideLoader()
    }
}