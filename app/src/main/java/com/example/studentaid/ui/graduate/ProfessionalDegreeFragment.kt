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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.studentaid.R
import com.example.studentaid.adapters.ProfessionalsAdapter
import com.example.studentaid.data.models.Job
import com.example.studentaid.data.models.ProfessionalDegree
import com.example.studentaid.databinding.FragmentProfessionalDegreeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_professional_degree.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfessionalDegreeFragment : Fragment() {


    lateinit var binding :FragmentProfessionalDegreeBinding

    private val ITJobsList = mutableListOf<ProfessionalDegree>()
    private val engineeringJobsList = mutableListOf<ProfessionalDegree>()

    lateinit var adapter :ProfessionalsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding =FragmentProfessionalDegreeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dummyData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ProfessionalsAdapter(requireContext(),null)

        val degreeItems = listOf("Information and Technologies", "Mechanical Engineering")

        val degreeAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_menu,degreeItems)
        binding.etDegree.setAdapter(degreeAdapter)

        binding.rvProfessional.adapter = adapter

        adapter.setOnApplyClickListener(object : ProfessionalsAdapter.OnApplyClickButton {
            override fun OnIClickListener(position: Int, message: String) {
                Toast.makeText(requireContext(),"you will $message",Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }

        })





        et_degree.setOnItemClickListener { parent, view, position, id ->
            updateDegrees(degreeItems[position])
        }

    }

    private fun dummyData(){


        ITJobsList.add(ProfessionalDegree("Getting: CISCO CCNA Certificate","Increase your score: Second Level","Increase your salary: 70 KD"))
        ITJobsList.add(ProfessionalDegree("Getting: Microsoft Office 360 Certificate","Increase your score: Second Level","Increase your salary: 50 KD"))
        engineeringJobsList.add(ProfessionalDegree("Getting: AutoCAD Certificate ","Increase your score: Second Level","Increase your salary: 45 KD"))
        engineeringJobsList.add(ProfessionalDegree("Getting: OSHA Certificate","Increase your score: Second Level","Increase your salary: 40 KD"))
    }


    private fun updateDegrees(degree: String) {
        adapter.changeData(getAppropriateJobs(degree))


    }
    private fun getAppropriateJobs(universityDegree:String):MutableList<ProfessionalDegree> {
        return if (universityDegree=="Information and Technologies"){
            ITJobsList
        }else if(universityDegree=="Mechanical Engineering"){
            engineeringJobsList
        }else {
            ITJobsList
        }

    }


}