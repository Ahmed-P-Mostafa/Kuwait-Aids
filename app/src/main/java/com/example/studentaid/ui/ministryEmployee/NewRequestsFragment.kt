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
import kotlinx.android.synthetic.main.fragment_new_requests.*


class NewRequestsFragment : BaseFragment() {
    private val TAG = "NewRequestsFragment"
    val adapter = RequestsAdapter(null)
    val studentList = mutableListOf<Student>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_newRequests.adapter= adapter
        adapter.SetOnRequestClickListener(object : RequestsAdapter.OnRequestClickListener {
            override fun onRequestClickListener(student: Student) {

                val action = NewRequestsFragmentDirections.actionNewRequestsFragmentToDetailsFragment(student)
                findNavController().navigate(action)
            }

        })


    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        getPendingRequests()


    }
    private fun getPendingRequests(){
        EmployeeDAO.getAllStudentsRequests({
            studentList.clear()
            it.documents.forEach {
                val student = it.toObject(Student::class.java)
                if (student != null && student.condition == Constants.CONDITION_PENDING) {
                    Log.d(TAG, "getPendingRequests: ${student.documentList?.size}")
                    studentList.add(student)
                }
            }
            if (studentList.size==0){
                iv_newEmptyList.visibility = View.VISIBLE
            }else iv_newEmptyList.visibility = View.GONE

            adapter.changeData(studentList)

        }, {
            showMessage(it.localizedMessage,requireContext())

        })
        hideLoader()
    }

}