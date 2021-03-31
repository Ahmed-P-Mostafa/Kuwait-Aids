package com.example.studentaid.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentaid.R
import com.example.studentaid.base.BaseFragment
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.utils.Constants
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_follow_student_request.*


class FollowStudentRequestFragment : BaseFragment() {
    var user:Student? = Student()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_follow_student_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // showLoader("Please wait...",requireContext())

    }

    override fun onStart() {
        super.onStart()
        getStudent()
    }

    private fun getStudent():Student{

        FirebaseAuth.getInstance().uid?.let { it ->
             StudentDao.getStudentFromFireStore(it, OnSuccessListener {st ->
                 handleStudentCondition(st.toObject(Student::class.java)!!)

            })
        }
        return user!!
    }
    private fun handleStudentCondition(student: Student){
      //  hideLoader()
        when(student.condition){
            Constants.CONDITION_APPROVED-> tv.text = "Your aid request approved"
            Constants.CONDITION_REJECTED-> tv.text = "your aid request suspended"
            Constants.CONDITION_DESERVED-> tv.text = "your aid request under examination for deserve"
            Constants.CONDITION_INCREASE-> tv.text = "your aid request under examination for increase"
            Constants.CONDITION_PENDING-> tv.text = "your aid request under examination"
        }
    }


}