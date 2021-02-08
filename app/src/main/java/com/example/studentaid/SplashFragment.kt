package com.example.studentaid

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isUSerLogedIn()){
            navigateToLandingFragment()
        }else{
            navigateToHomeFragment()
        }


    }

    fun isUSerLogedIn():Boolean{
        return (Application().isUserLogidIn())
    }
    fun navigateToLandingFragment(){
        val handler = Handler().postDelayed(Runnable {

            val controller = findNavController()

            controller.navigate(R.id.action_splashFragment_to_landingFragment)



        },3000)
    }
    fun navigateToHomeFragment(){}


}