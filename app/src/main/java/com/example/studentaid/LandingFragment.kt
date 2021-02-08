package com.example.studentaid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_landing.*


class LoginFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()

        btnStudent.setOnClickListener {
           // navigateToStudentRegister()
            controller.navigate(R.id.action_landingFragment_to_studentRegisterFragment)
        }
        btnMinyEmployee.setOnClickListener {
            //navigateToMinistryEmployeeRegister()
            controller.navigate(R.id.action_landingFragment_to_ministryEmployeeRegisterFragment)
        }
        btnUniEmployee.setOnClickListener {
          //  navigateToUniversityEmployeeRegister()
            controller.navigate(R.id.action_landingFragment_to_facultyEmployeeRegisterFragment)
        }

    }
    /*
    private fun navigateToStudentRegister(){
        controller.navigate(R.id.action_landingFragment_to_studentRegisterFragment)

    }
    private fun navigateToUniversityEmployeeRegister(){
        controller.navigate(R.id.action_landingFragment_to_facultyEmployeeRegisterFragment)

    }
  private fun navigateToMinistryEmployeeRegister(){
      controller.navigate(R.id.action_landingFragment_to_ministryEmployeeRegisterFragment)
    }*/


}