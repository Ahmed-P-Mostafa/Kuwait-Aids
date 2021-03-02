package com.example.studentaid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentaid.data.models.Student
import com.example.studentaid.databinding.RequestLayoutBinding

open class RequestsAdapter(var list:List<Student>?):RecyclerView.Adapter<RequestsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RequestLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list?.get(position)
        holder.bind(student!!)
        holder.itemView.setOnClickListener {
            listener?.onRequestClickListener(student)
        }
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
    fun changeData(list :List<Student>){
        this.list = list
        notifyDataSetChanged()
    }

    interface OnRequestClickListener{
        fun onRequestClickListener(student: Student)
    }
    private var listener :OnRequestClickListener?=null

    fun SetOnRequestClickListener(listener: OnRequestClickListener){
        this.listener = listener
    }



}