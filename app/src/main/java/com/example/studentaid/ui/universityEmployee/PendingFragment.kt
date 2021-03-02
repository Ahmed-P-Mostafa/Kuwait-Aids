package com.example.studentaid.ui.universityEmployee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.studentaid.R
import com.example.studentaid.adapters.AcceptedRequestsAdapter
import com.example.studentaid.adapters.RequestsAdapter
import com.example.studentaid.base.BaseFragment
import com.example.studentaid.data.models.PushNotification
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.Client
import com.example.studentaid.data.onlineDatabase.EmployeeDAO
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.fragment_accepted_requests.*
import kotlinx.android.synthetic.main.fragment_pending.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class PendingFragment : BaseFragment() {
    private val TAG = "PendingFragment"

    val studentList = mutableListOf<Student>()
    val adapter = RequestsAdapter(null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_pendingRequests.adapter = adapter
        getpendingRequests()

        adapter.SetOnRequestClickListener(object : RequestsAdapter.OnRequestClickListener {
            override fun onRequestClickListener(student: Student) {

                val action =  PendingFragmentDirections.actionPendingFragmentToRequestDetailsFragment(student)
                findNavController().navigate(action)
            }

        })

    }
    private fun getpendingRequests(){
        showLoader("Loading...",requireContext())
        EmployeeDAO.getAllStudentsRequests({
            studentList.clear()
            it.documents.forEach {
                val student = it.toObject(Student::class.java)
                if (student != null && student.condition == Constants.CONDITION_NULL) {
                    Log.d(TAG, "getPendingRequests: ${student.condition}")
                    studentList.add(student)
                }
            }
            if (studentList.size ==0) iv_pendingEmptyList.visibility = View.VISIBLE

            adapter.changeData(studentList)

        }, {
            showMessage(it.localizedMessage,requireContext())

        })
        hideLoader()
    }


}