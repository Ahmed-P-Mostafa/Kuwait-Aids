package com.example.studentaid.ui.ministryEmployee

import android.content.Context
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
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.example.studentaid.R
import com.example.studentaid.base.BaseFragment
import com.example.studentaid.data.models.NotificationData
import com.example.studentaid.data.models.PushNotification
import com.example.studentaid.data.onlineDatabase.Client
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.databinding.FragmentDetailsBinding
import com.example.studentaid.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.fragment_request_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsFragment : BaseFragment() {
    val args :DetailsFragmentArgs by navArgs()
    private val TAG = "DetailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this

        val binding : FragmentDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        binding.p = args.student
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val message =""

        btn_accept.setOnClickListener {
            if (args.student.condition==Constants.CONDITION_STOP){
                Log.d(TAG, "onViewCreated: accept button and condition Stop")
                updateStudentCondition(Constants.CONDITION_REJECTED)

            }else if (args.student.condition==Constants.CONDITION_DESERVED){
                updateStudentCondition(Constants.CONDITION_APPROVED)
                    showAmountDialog(args.student.token!!,requireContext())
                Log.d(TAG, "onViewCreated: accept button and condition desereved")

            }else if (args.student.condition==Constants.CONDITION_INCREASE){
                updateStudentCondition(Constants.CONDITION_APPROVED)
                showAmountDialog(args.student.token!!,requireContext())
                Log.d(TAG, "onViewCreated: accept button and condition increasee")

            }

        }
        btn_refuse.setOnClickListener {
            if (args.student.condition==Constants.CONDITION_STOP){
                updateStudentCondition(Constants.CONDITION_APPROVED)
                Log.d(TAG, "onViewCreated: refuse button and condition desereved")


            }else if (args.student.condition==Constants.CONDITION_DESERVED){
                Log.d(TAG, "onViewCreated: refuse button and condition Stop")

                updateStudentCondition(Constants.CONDITION_REJECTED)
                showAmountDialog(args.student.token!!,requireContext())
            }else if (args.student.condition==Constants.CONDITION_INCREASE){
                updateStudentCondition(Constants.CONDITION_APPROVED)
                showAmountDialog(args.student.token!!,requireContext())
                Log.d(TAG, "onViewCreated: refuse button and condition increase")

            }

        }
        btn_attached.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToDocumentsFragment(student = args.student)
               findNavController().navigate(action)
        }
    }
    private fun showAmountDialog(to:String,context: Context){
       val dialog =  MaterialDialog(context).title(text = "Enter message").positiveButton(R.string.send).input { materialDialog, charSequence ->
           sendNotification(PushNotification(to, NotificationData(getString(R.string.app_name),charSequence.toString())))
           findNavController().navigateUp()

       }
        dialog.show()

    }
    private fun updateStudentCondition(condition:String){
        CoroutineScope(Dispatchers.IO).launch {
              StudentDao.updateStudentCondition(args.student.id!!, condition,
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

}