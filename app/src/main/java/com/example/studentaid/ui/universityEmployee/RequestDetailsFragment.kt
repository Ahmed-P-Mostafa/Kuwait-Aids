package com.example.studentaid.ui.universityEmployee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.studentaid.R
import com.example.studentaid.data.models.NotificationData
import com.example.studentaid.data.models.PushNotification
import com.example.studentaid.data.onlineDatabase.Client
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.databinding.FragmentRequestDetailsBinding
import com.example.studentaid.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.fragment_request_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class RequestDetailsFragment : Fragment() {
    val args :RequestDetailsFragmentArgs by navArgs()
    private val TAG = "RequestDetailsFragment"
    private var message = "Your Request have updated"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        // Inflate the layout for this fragment
        val binding:FragmentRequestDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_request_details,container,false)
      //  binding.p = args.student
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        if (args.st.condition==Constants.CONDITION_PENDING||args.st.condition==Constants.CONDITION_NULL){
            btn_refuse.isEnabled = true
            btn_accept.isEnabled = true
        }
        if (args.st.condition==Constants.CONDITION_NULL){
            btn_attached.isEnabled = false
        }
        btn_accept.setOnClickListener {
            if (args.st.condition==Constants.CONDITION_NULL){
                updateStudentCondition(Constants.CONDITION_ACCESSED)
            }else{
                updateStudentCondition(Constants.CONDITION_APPROVED)
            }
        }
        btn_refuse.setOnClickListener {
            updateStudentCondition(Constants.CONDITION_REJECTED)
        }
        btn_attached.setOnClickListener {
      val action =   RequestDetailsFragmentDirections.actionRequestDetailsFragmentToAttachmentsFragment(args.st)
            findNavController().navigate(action)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }


    private fun updateStudentCondition(condition:String){
        CoroutineScope(IO).launch {
            StudentDao.updateStudentCondition(args.st.id!!, condition,
                OnCompleteListener {
                    if (it.isSuccessful) {
                        sendNotification(
                            PushNotification(
                                args.st.token!!,
                                NotificationData("Student Aid", message)
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            "Request Approved Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
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
        CoroutineScope(IO).launch {
            try {
                val response = Client.api.sendNotification(notification)
                if (response.isSuccessful){
                    Log.d(TAG, "sendNotification: notification Approved")



                }else{
                    Log.d(TAG, "sendNotification: notification error occurred")
                }

            }catch (e:Exception){
                Log.d(TAG, "sendNotification: ${e.localizedMessage}")
            }
        }

    }

}