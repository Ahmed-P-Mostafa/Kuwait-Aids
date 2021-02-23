package com.example.studentaid.ui.graduate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.studentaid.R
import com.example.studentaid.adapters.ProfessionalsAdapter
import com.example.studentaid.data.models.Job
import com.example.studentaid.data.models.ProfessionalDegree
import com.example.studentaid.databinding.FragmentProfessionalDegreeBinding
import kotlinx.android.synthetic.main.fragment_professional_degree.*


class ProfessionalDegreeFragment : Fragment() {


    lateinit var binding :FragmentProfessionalDegreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding =FragmentProfessionalDegreeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val degreeItems = listOf("Engineering School", "Medicine School", "Law School")
        val degreeAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_menu,degreeItems)
        binding.etDegree.setAdapter(degreeAdapter)

        binding.rvProfessional.adapter = ProfessionalsAdapter(dummyData())



        et_degree.setOnItemClickListener { parent, view, position, id ->
         Toast.makeText(requireContext(),degreeItems[position],Toast.LENGTH_LONG).show()
        }
    }

    private fun dummyData():List<ProfessionalDegree>{
        val list = mutableListOf<ProfessionalDegree>()
        list.add(ProfessionalDegree())
        list.add(ProfessionalDegree())
        list.add(ProfessionalDegree())

        return list
    }


}