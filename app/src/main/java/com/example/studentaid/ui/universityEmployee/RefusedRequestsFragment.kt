package com.example.studentaid.ui.universityEmployee

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.studentaid.R
import com.example.studentaid.adapters.RefusedRequestsAdapter
import com.example.studentaid.base.BaseFragment
import com.example.studentaid.data.models.NotificationData
import com.example.studentaid.data.models.PushNotification
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.Client
import com.example.studentaid.data.onlineDatabase.EmployeeDAO
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_accepted_requests.*
import kotlinx.android.synthetic.main.fragment_pending.*
import kotlinx.android.synthetic.main.fragment_refused_requests.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RefusedRequestsFragment : BaseFragment() {
    private val TAG = "RefusedRequestsFragment"
    val adapter = RefusedRequestsAdapter(null)
    val studentList = mutableListOf<Student>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_refused_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_refusedRequests.adapter = adapter
        getRejectedRequests()

        adapter.SetOnRequestClickListener(object : RefusedRequestsAdapter.OnRequestClickListener {
            override fun onStopClickListener(student: Student,position:Int) {

                val list = arrayOf("He returned as a continuous student","His status has been modified")
                showMaterialDialog(position,student,"Reason for deserve aid",list)

            }

            override fun onOpenClickListener(student: Student,position:Int) {
                val action = RefusedRequestsFragmentDirections.actionRefusedRequestsFragmentToRequestDetailsFragment(student)
                findNavController().navigate(action)

            }

        })
    }
    private fun getRejectedRequests(){
        EmployeeDAO.getAllStudentsRequests({

            studentList.clear()
            it.documents.forEach {
                val student = it.toObject(Student::class.java)
                if (student != null && student.condition == Constants.CONDITION_REJECTED) {
                    Log.d(TAG, "getPendingRequests: ${student.condition}")
                    studentList.add(student)
                }
            }
            if (studentList.size ==0) iv_refusedEmptyList.visibility = View.VISIBLE
            else iv_refusedEmptyList.visibility = View.GONE
            adapter.changeData(studentList)

        }, {
            showMessage(it.localizedMessage,requireContext())

        })
        hideLoader()
    }

    private fun updateStudentCondition(position:Int,student: Student, condition:String){

        CoroutineScope(Dispatchers.IO).launch {
            StudentDao.updateStudentCondition(student.id!!, condition,
                OnCompleteListener {
                    if (it.isSuccessful) {
                        studentList.removeAt(position)
                        adapter.notifyDataSetChanged()

                    } else if (!it.isSuccessful) {
                        Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(TAG, "updateStudentCondition: ${it.exception}")

                    }
                }
            )
        }

    }

    private fun sendNotification(notification: PushNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = Client.api.sendNotification(notification)
                if (response.isSuccessful){
                    Log.d(TAG, "sendNotification: notification Approved")

                }else{
                    Log.d(TAG, "sendNotification: notification error occurred")
                }

            }catch (e: Exception){
                Log.d(TAG, "sendNotification: ${e.localizedMessage}")
            }
        }

    }

    private fun showMaterialDialog(position: Int,student: Student,title:String, list :Array<String>){
        val items = list

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setItems(items) { dialog, which ->
                // Respond to item chosen


                Toast.makeText(requireContext(), items[which].toString(), Toast.LENGTH_LONG).show()
               // getRejectedRequests()
                //jobsList = getAppropriateJobs(items[which])
                //showJobsList()
                CoroutineScope(Dispatchers.IO).launch {
                    updateStudentCondition(position,student,Constants.CONDITION_DESERVED)

                    sendNotification(PushNotification(Constants.Ministry_Topic,
                        NotificationData("Student Aid","please Deserve this student aid because of his ${items[which]} grades")
                    ))
                }
            }
            .setCancelable(false)
            .show()


    }


}