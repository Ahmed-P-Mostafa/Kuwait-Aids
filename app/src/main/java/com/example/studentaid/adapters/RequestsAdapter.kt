package com.example.studentaid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentaid.data.models.Student
import com.example.studentaid.databinding.RequestLayoutBinding

class RequestsAdapter(val list:List<Student>?):RecyclerView.Adapter<RequestsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RequestLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list?.get(position)
        holder.bind(student!!)
    }

    override fun getItemCount()=list?.size?:0


    class ViewHolder(private val binding: RequestLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Student){
            with(binding){
                student = item
                executePendingBindings()
            }
        }
    }


}