package com.example.studentaid.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentaid.R
import com.example.studentaid.data.models.Job
import com.example.studentaid.databinding.JobItemLayoutBinding

class GraduateJobsAdapter(private var list :List<Job>):RecyclerView.Adapter<GraduateJobsAdapter.ViewHolder>() {

    private val TAG = "GraduateJobsAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = JobItemLayoutBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            Log.d(TAG, "onBindViewHolder:${holder.adapterPosition} ")
            listener?.onJobClicked(item)
        }
    }

    override fun getItemCount()= list.size

    class ViewHolder(private val binding :JobItemLayoutBinding) :RecyclerView.ViewHolder(binding.root){

        fun bind(item:Job){
            with(binding){
                imageView.setImageResource(item.imageSrcId)
                tvOccupation.text = item.occupation
                tvHours.text = item.workingHours
                tvPlace.text = item.location
                tvPlaces.text = item.placesAvailable
                tvSalary.text = item.salary
                tvTitle.text = item.title
            }
        }

    }
    fun changeData(list:List<Job>){
        this.list = list
        notifyDataSetChanged()

    }
    interface OnJobClickListener{
        fun onJobClicked(job: Job)

    }
    private var listener:OnJobClickListener?=null
    fun setOnJobClickListener( listener:OnJobClickListener){
        this.listener = listener


    }

}