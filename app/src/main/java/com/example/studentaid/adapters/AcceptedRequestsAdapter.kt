package com.example.studentaid.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentaid.data.models.Student
import com.example.studentaid.databinding.AcceptedRequestLayoutBinding
import com.example.studentaid.databinding.RequestLayoutBinding
import kotlinx.android.synthetic.main.accepted_request_layout.view.*

open class AcceptedRequestsAdapter(var list:List<Student>?):RecyclerView.Adapter<AcceptedRequestsAdapter.ViewHolder>() {
    private val TAG = "AcceptedRequestsAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AcceptedRequestLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list?.get(position)
        holder.bind(student!!)

        holder.itemView.tv_stop.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: on stop click listener ")
            listener?.onStopClickListener(student,position)
        }
        holder.itemView.tv_increase.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: on increase click listener ")
            listener?.onIncreaseClickListener(student,position)
        }
        holder.itemView.tv_open.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: on open click listener ")
            listener?.onOpenClickListener(student,position)
        }
    }

    override fun getItemCount()=list?.size?:0


    class ViewHolder(private val binding: AcceptedRequestLayoutBinding):RecyclerView.ViewHolder(binding.root) {
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
    fun removeItem(){

    }

    interface OnRequestClickListener{
        fun onStopClickListener(student: Student,position:Int)
        fun onIncreaseClickListener(student: Student,position:Int)
        fun onOpenClickListener(student: Student,position:Int)
    }
    private var listener :OnRequestClickListener?=null

    fun SetOnRequestClickListener(listener: OnRequestClickListener){
        this.listener = listener
    }



}