package com.example.studentaid.ui.ministryEmployee

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_increase_aids.*


class IncreaseAidsFragment : BaseFragment() {
    private val TAG = "IncreaseAidsFragment"

    val adapter = RequestsAdapter(null)
    val studentList = mutableListOf<Student>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_increase_aids, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        showLoader("Loading Requests...",requireContext())
        rv_increase.adapter = adapter

        adapter.SetOnRequestClickListener(object : RequestsAdapter.OnRequestClickListener {
            override fun onRequestClickListener(student: Student) {
                val action = IncreaseAidsFragmentDirections.actionIncreaseAidsFragmentToDetailsFragment(student)
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
                if (student != null && student.condition == Constants.CONDITION_INCREASE) {
                    Log.d(TAG, "getPendingRequests: ${student.condition}")
                    studentList.add(student)
                }
            }
            if (studentList.size==0){
                iv_increaseEmptyList.visibility = View.VISIBLE
            }
            adapter.changeData(studentList)

        }, {
            showMessage(it.localizedMessage,requireContext())

        })
        hideLoader()
    }


}