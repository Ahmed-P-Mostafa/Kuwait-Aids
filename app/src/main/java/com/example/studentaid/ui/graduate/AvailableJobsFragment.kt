package com.example.studentaid.ui.graduate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.studentaid.R
import com.example.studentaid.adapters.GraduateJobsAdapter
import com.example.studentaid.data.models.Job
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_available_jobs.*
import kotlinx.android.synthetic.main.fragment_course.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class AvailableJobsFragment : Fragment() {
    private val TAG = "AvailableJobsFragment"
    private var adapter = GraduateJobsAdapter(null)
    private var jobsList = mutableListOf<Job>()
    var degree = MutableLiveData<String>()
    val degreeItems = listOf("Information and Technologies", "Mechanical Engineering")

    /*   lateinit var navHostFragment: NavHostFragment
       lateinit var navController: NavController*/




    private var student: Student?=null

    private val ITJobsList = mutableListOf<Job>()
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

        val degreeAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_menu,degreeItems)
        et_certificate.setAdapter(degreeAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   navHostFragment =parentFragmentManager.findFragmentById(R.id.fragment_graduate_nav_host) as NavHostFragment
       // navController = navHostFragment.navController
        graduateJobsRecyclerView.adapter = adapter

        degree.observe(viewLifecycleOwner, Observer {
            adapter.changeData(getAppropriateJobs(it))
        })
        dummyJobs()

       /* CoroutineScope(Dispatchers.IO).launch {

            //checkForUniversityDegree()
        }*/


        et_certificate.setOnItemClickListener { parent, view, position, id ->
            updateDegrees(degreeItems[position])
        }



        adapter.setOnJobClickListener(object : GraduateJobsAdapter.OnJobClickListener {
            override fun onJobClicked(job: Job) {
                Log.d("AvailableJobsFragment", "onJobClicked: ${job.occupation}")
               // val bundle = bundleOf("job" to job)
                val action = AvailableJobsFragmentDirections.actionAvailableJobsFragmentToJobDetailsFragment(job)

                findNavController().navigate(action)

            }

        })


    }

    private fun updateDegrees(s: String) {
        adapter.changeData(getAppropriateJobs(s))
    }

    /*private fun checkForUniversityDegree(){
        Log.d(TAG, "checkForRequest: get userFrom shared preferences")
        //    val person = Utils.getUserFromSharedPreferences(this)
        StudentDao.getStudentFromFireStore(FirebaseAuth.getInstance().currentUser?.uid!!, OnSuccessListener {
            Log.e(TAG, "onCreate: succed")
            student = it.toObject(Student::class.java)!!
            Log.e(TAG, "onCreate: ${student?.universityDegree}")
            if (student?.universityDegree.equals(null)){
                Log.d(TAG, "checkForRequest: ${student?.condition}")
                //showSpinnerDialog()
                //showMaterialDialog()
            }
            else{

                adapter.changeData(getAppropriateJobs(student?.universityDegree!!))
            }
        })
    }*/

 /*   private fun showJobsList() {
        adapter.changeData(getAppropriateJobs(student?.universityDegree!!))

    }*/

    private fun showMaterialDialog(){
        val items = arrayOf("Information and Technologies", "Mechanical Engineering")

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Determine your university degree")
            .setItems(items) { dialog, which ->
                // Respond to item chosen
              //  degree.value = items[which]
                Toast.makeText(requireContext(), items[which].toString(), Toast.LENGTH_LONG).show()
                //jobsList = getAppropriateJobs(items[which])
                //showJobsList()
                CoroutineScope(IO).launch {

                    updateGraduateDegree(items[which])
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun updateGraduateDegree(updatedDegree:String){

        StudentDao.updateGraduateDegree(FirebaseAuth.getInstance().currentUser?.uid!!,updatedDegree, OnCompleteListener {
            if (it.isSuccessful){
                adapter.changeData( getAppropriateJobs(updatedDegree))
            }
        })
    }
    private fun getAppropriateJobs(universityDegree:String):MutableList<Job> {
        return if (universityDegree=="Information and Technologies"){
            ITJobsList
        }else if(universityDegree=="Mechanical Engineering"){
            engineeringJobsList
        }else {
            ITJobsList
        }

    }



    private fun dummyJobs(){
        ITJobsList.clear()
        engineeringJobsList.clear()

        ITJobsList.add(Job(R.drawable.hiring,"Programmer","Minister of finance","8:00AM to 2:00PM","Programming Driver","400 KD","2"))


        engineeringJobsList.add(Job(R.drawable.hiring1,"Engineering in mechanic field","Minister of Public Works ","7:00 AM to 1:30 PM","Engineer","550 KD","2"))


    }



}