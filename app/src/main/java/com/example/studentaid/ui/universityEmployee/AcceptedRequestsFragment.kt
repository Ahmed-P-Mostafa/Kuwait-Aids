package com.example.studentaid.ui.universityEmployee

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.example.studentaid.R
import com.example.studentaid.adapters.AcceptedRequestsAdapter
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_accepted_requests.*
import kotlinx.android.synthetic.main.fragment_refused_requests.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class AcceptedRequestsFragment : BaseFragment(){

    private val TAG = "AcceptedRequestsFragmen"

    val studentList = mutableListOf<Student>()
    val adapter = AcceptedRequestsAdapter(null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accepted_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_acceptedRequests.adapter = adapter
        getApprovedRequests()
        adapter.SetOnRequestClickListener(object : AcceptedRequestsAdapter.OnRequestClickListener {
            override fun onStopClickListener(student: Student,position:Int) {

                val list = arrayOf("withdrawal","full dismissal","partial dismissal","freezing")
                showMaterialDialog(student.id!!,"Reason for stop aid",list)
                updateStudentCondition(student,Constants.CONDITION_STOP)
                studentList.removeAt(position)
                adapter.notifyDataSetChanged()

            }

            override fun onIncreaseClickListener(student: Student,position:Int){
                updateStudentCondition(student,Constants.CONDITION_INCREASE)
                sendNotification(PushNotification(Constants.Ministry_Topic,
                    NotificationData(getString(R.string.app_name),"Please increase the aid for this student as his grades i n final exams")
                ))
                studentList.removeAt(position)
                adapter.notifyDataSetChanged()
            }

            override fun onOpenClickListener(student: Student,position:Int) {
                val action = AcceptedRequestsFragmentDirections.actionAcceptedRequestsFragmentToRequestDetailsFragment(student)
                findNavController().navigate(action)

            }

        })
    }
    private fun getApprovedRequests(){
        showLoader("Loading...",requireContext())
        EmployeeDAO.getAllStudentsRequests({
            studentList.clear()
            it.documents.forEach {
                val student = it.toObject(Student::class.java)
                if (student != null && student.condition == Constants.CONDITION_APPROVED) {
                    Log.d(TAG, "getPendingRequests: ${student.condition}")
                    studentList.add(student)
                }
            }
            if (studentList.size ==0) iv_acceptedEmptyList.visibility = View.VISIBLE
            else iv_acceptedEmptyList.visibility = View.GONE

            adapter.changeData(studentList)

        }, {
            showMessage(it.localizedMessage,requireContext())

        })
        hideLoader()
    }



    private fun updateStudentCondition(student: Student, condition:String){

        CoroutineScope(Dispatchers.IO).launch {
            StudentDao.updateStudentCondition(student.id!!, condition,
                OnCompleteListener {
                    if (it.isSuccessful) {

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

    private fun showMaterialDialog(id:String,title:String, list :Array<String>){
        val items = list

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setItems(items) { dialog, which ->

                Toast.makeText(requireContext(), items[which].toString(), Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.IO).launch {

                    StudentDao.updateStudentMessage(id,items[which], OnCompleteListener {
                        if (it.isSuccessful){

                            sendNotification(PushNotification(Constants.Ministry_Topic,
                                NotificationData("Student Aid","please Stop this student aid because of ${items[which]} reason")
                            ))
                        }else{
                            Snackbar.make(requireView(),it.exception?.localizedMessage.toString(),Snackbar.LENGTH_SHORT).show()
                        }
                    })

                }
            }
            .setCancelable(false)
            .show()
    }

}