package com.example.studentaid.ui.graduate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.studentaid.R
import com.example.studentaid.databinding.FragmentJobDetailsBinding


class JobDetailsFragment : Fragment() {

    val args :JobDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding :FragmentJobDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_job_details,container,false)

        binding.job = args.job
        binding.ivBanner.setImageResource(args.job.imageSrcId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




    }

  



}