package com.example.studentaid.ui.ministryEmployee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.example.studentaid.R
import com.example.studentaid.adapters.RequestsAdapter
import com.example.studentaid.base.BaseFragment
import com.example.studentaid.data.models.NotificationData
import com.example.studentaid.data.models.PushNotification
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.Client
import com.example.studentaid.data.onlineDatabase.EmployeeDAO
import com.example.studentaid.ui.universityEmployee.AllRequestsFragmentDirections
import com.example.studentaid.utils.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_all_requests.*
import kotlinx.android.synthetic.main.fragment_suspended.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class SuspendedFragment : BaseFragment() {
    private val TAG = "SuspendedFragment"
    val adapter = RequestsAdapter(null)
    val studentList = mutableListOf<Student>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suspended, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d(TAG, "onViewCreated: ")
        showLoader("Loading Requests...",requireContext())
        rv_suspended.adapter = adapter

        adapter.SetOnRequestClickListener(object : RequestsAdapter.OnRequestClickListener {
            override fun onRequestClickListener(student: Student) {
                val action = SuspendedFragmentDirections.actionSuspendedFragmentToDetailsFragment(student)
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
                if (student != null && student.condition == Constants.CONDITION_STOP) {
                    Log.d(TAG, "getPendingRequests: ${student.condition}")
                    studentList.add(student)
                }
            }
            if (studentList.size==0){
                iv_suspendedEmptyList.visibility = View.VISIBLE
            }
            adapter.changeData(studentList)

        }, {
            showMessage(it.localizedMessage,requireContext())

        })
        hideLoader()
    }


}