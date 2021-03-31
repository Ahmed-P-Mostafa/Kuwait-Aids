package com.example.studentaid.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.studentaid.data.models.Course
import com.example.studentaid.data.models.ProfessionalDegree
import com.example.studentaid.databinding.CourseItemLayoutBinding
import kotlinx.android.synthetic.main.course_item_layout.view.*

class CoursesAdapter(private var list: List<Course>?):RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {
    val TAG = "CoursesAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding  = CourseItemLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list?.get(holder.adapterPosition)
        holder.bind(item!!)
        Log.d(TAG, "onBindViewHolder: ${item.name}")
        Log.d(TAG, "onBindViewHolder: ${item.desc}")
        holder.itemView.btn_apply.setOnClickListener {
            listener?.OnIClickListener(position,item.name!!)
        }


    }


    override fun getItemCount()= list?.size?:0

    fun changeData(appropriateJobs: List<Course>) {
        this.list = appropriateJobs
        notifyDataSetChanged()
    }


    class ViewHolder(private val binding:CourseItemLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Course){

            Log.d("CoursesAdapter", "bind:${item.name}")
            Log.d("CoursesAdapter", "bind: ${item.desc}")
            with(binding){
                course = item
                ivImage.setImageResource(item.imageSrc!!)
                executePendingBindings()
            }

        }

    }

    interface OnApplyClickButton{
        fun OnIClickListener(position:Int,message:String)
    }

    var listener:OnApplyClickButton?=null

    fun setOnApplyClickListener(listener:OnApplyClickButton){
        this.listener = listener
    }
}