package com.example.studentaid.ui.graduate

import android.graphics.Path
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.studentaid.R
import com.example.studentaid.adapters.GraduateJobsAdapter
import com.example.studentaid.data.models.Job
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_available_jobs.*


class AvailableJobsFragment : Fragment() {
    private val TAG = "AvailableJobsFragment"
    private lateinit var adapter : GraduateJobsAdapter
    private lateinit var jobsList :MutableList<Job>
  /*   lateinit var navHostFragment: NavHostFragment
     lateinit var navController: NavController*/




    private var student: Student?=null

    private val medicineJobsList = mutableListOf<Job>()
    private val lawJobsList = mutableListOf<Job>()
    private val engineeringJobsList = mutableListOf<Job>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_jobs, container, false)
    }

    override fun onStart() {
        super.onStart()
        adapter.changeData(jobsList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   navHostFragment =parentFragmentManager.findFragmentById(R.id.fragment_graduate_nav_host) as NavHostFragment
       // navController = navHostFragment.navController

        dummyJobs()
        jobsList = mutableListOf()
        adapter = GraduateJobsAdapter(jobsList)
        checkForUniversityDegree()

        graduateJobsRecyclerView.adapter = adapter
        adapter.setOnJobClickListener(object : GraduateJobsAdapter.OnJobClickListener {
            override fun onJobClicked(job: Job) {
                Log.d("AvailableJobsFragment", "onJobClicked: ${job.occupation}")
                val bundle = bundleOf("job" to job)
                val action = AvailableJobsFragmentDirections.actionAvailableJobsFragmentToJobDetailsFragment(job)

                findNavController().navigate(action)

            }

        })


    }
    private fun checkForUniversityDegree(){
        Log.d(TAG, "checkForRequest: get userFrom shared preferences")
        //    val person = Utils.getUserFromSharedPreferences(this)
        StudentDao.getStudentFromFireStore(FirebaseAuth.getInstance().currentUser?.uid!!, OnSuccessListener {
            Log.d(TAG, "onCreate: succed")
            student = it.toObject(Student::class.java)!!
            Log.d(TAG, "onCreate: ${student?.universityDegree}")
            if (student?.universityDegree.equals(null)){
                Log.d(TAG, "checkForRequest: ${student?.condition}")
                //showSpinnerDialog()
                showMaterialDialog()
            }
            else{
               jobsList =  getAppropriateJobs(student?.universityDegree!!)
                adapter.changeData(jobsList)
            }
        })
    }

    private fun showJobsList() {
        adapter.changeData(getAppropriateJobs(student?.universityDegree!!))

    }

    private fun showMaterialDialog(){
        val items = arrayOf("Engineering School", "Medicine School", "Law School")

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Determine your university degree")
            .setItems(items) { dialog, which ->
                // Respond to item chosen
                Toast.makeText(requireContext(), items[which].toString(), Toast.LENGTH_LONG).show()
                updateGraduateDegree(items[which])
            }
            .setCancelable(false)
            .show()
    }

    private fun updateGraduateDegree(updatedDegree:String){
        StudentDao.updateGraduateDegree(FirebaseAuth.getInstance().currentUser?.uid!!,updatedDegree, OnCompleteListener {
            if (it.isSuccessful){
                jobsList = getAppropriateJobs(updatedDegree)
                showJobsList()
            }
        })
    }
    private fun getAppropriateJobs(universityDegree:String)=
        when(universityDegree){
            "Civil Engineering"->{
                engineeringJobsList
            }
            "Medicine School"->{
                medicineJobsList
            }
            else ->lawJobsList

        }

    private fun dummyJobs(){
        medicineJobsList.add(Job(R.drawable.hiring,"Doctor","Kuwait","8","Dentist","8000","5"))
        medicineJobsList.add(Job(R.drawable.hiring1,"Doctor","Kuwait","8","Occupational Health Doctor","8000","2"))
        medicineJobsList.add(Job(R.drawable.hiring1,"Doctor","Kuwait","8","Ward Manager","12000","1"))
        medicineJobsList.add(Job(R.drawable.hiring,"Doctor","Kuwait","8","Medical Representative","5000","5"))
        medicineJobsList.add(Job(R.drawable.hiring1,"Doctor","Kuwait","8","Veterinary Medical","8000","5"))

        lawJobsList.add(Job(R.drawable.hiring1,"Lawyer","Kuwait","8","Civil Law specialist","8000","5"))
        lawJobsList.add(Job(R.drawable.hiring,"Lawyer","Kuwait","8","Criminal Law specialist","8000","1"))
        lawJobsList.add(Job(R.drawable.hiring,"Lawyer","Kuwait","8","Administrative Law specialist","9000","2"))
        lawJobsList.add(Job(R.drawable.hiring1,"Lawyer","Kuwait","8","Public Law specialist","5000","1"))
        lawJobsList.add(Job(R.drawable.hiring,"Lawyer","Kuwait","8","Insurance Law specialist","7000","3"))

        engineeringJobsList.add(Job(R.drawable.hiring1,"Petroleum Engineer","Kuwait","8","Petroleum Engineer","8000","5"))
        engineeringJobsList.add(Job(R.drawable.hiring,"Petroleum Engineer","Kuwait","8","Petroleum Engineer","8000","5"))
        engineeringJobsList.add(Job(R.drawable.hiring1,"Petroleum Engineer","Kuwait","8","Petroleum Engineer","8000","5"))
        engineeringJobsList.add(Job(R.drawable.hiring,"Petroleum Engineer","Kuwait","8","Petroleum Engineer","8000","5"))
        engineeringJobsList.add(Job(R.drawable.hiring1,"Petroleum Engineer","Kuwait","8","Petroleum Engineer","8000","5"))


    }



}