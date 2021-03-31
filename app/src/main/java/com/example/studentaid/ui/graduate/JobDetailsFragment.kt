package com.example.studentaid.ui.graduate

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.studentaid.R
import com.example.studentaid.databinding.FragmentJobDetailsBinding


class JobDetailsFragment : Fragment() {

    val args :JobDetailsFragmentArgs by navArgs()
    lateinit var binding :FragmentJobDetailsBinding
    private val TAG = "JobDetailsFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_job_details,container,false)

        binding.job = args.job
        binding.ivBanner.setImageResource(args.job.imageSrcId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnUniversityDegree.setOnClickListener {
            pickImage(1)
        }
        binding.btnIdFace.setOnClickListener {
            pickImage(2)
        }
        binding.btnIdBack.setOnClickListener {
            pickImage(3)
        }
        binding.btnSend.setOnClickListener {
            Toast.makeText(requireContext(),"You applied for ${args.job.title} job.",Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

    }

    private fun pickImage(code:Int) {

        Log.d(TAG, "pickImage: ")

        val pickImageIntent = Intent(Intent.ACTION_PICK)
        pickImageIntent.type = "image/*"

        startActivityForResult(pickImageIntent, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode==RESULT_OK&&data!=null){
            when(requestCode){
                1-> binding.tvUniversityDegree.text = "1/1"
                2-> binding.tvIdFace.text = "1/1"
                3-> binding.tvIdBack.text = "1/1"
            }
            Toast.makeText(requireContext(),"Image uploaded",Toast.LENGTH_SHORT).show()
        }

    }

  



}