package com.example.studentaid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.studentaid.R
import com.example.studentaid.data.models.ProfessionalDegree
import com.example.studentaid.databinding.ProfessionalItemLayoutBinding
import kotlinx.android.synthetic.main.course_item_layout.view.*
import kotlinx.android.synthetic.main.fragment_professional_degree.view.*

class ProfessionalsAdapter(val context: Context,var list:List<ProfessionalDegree>?) :RecyclerView.Adapter<ProfessionalsAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProfessionalItemLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val item = list?.get(position)
        holder.bind(list?.get(position)!!)
        holder.itemView.btn_apply.setOnClickListener {
            listener?.OnIClickListener(position,item?.score!!)
        }
    }

    override fun getItemCount()=list?.size?:0

    class ViewHolder(private val binding: ProfessionalItemLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(item:ProfessionalDegree){
            with(binding){
               p = item
                invalidateAll()
            }

        }

    }
    fun changeData( list:List<ProfessionalDegree>){
        this.list = list
        notifyDataSetChanged()
    }

    interface OnApplyClickButton{
        fun OnIClickListener(position:Int,message:String)
    }

    var listener:OnApplyClickButton?=null

    fun setOnApplyClickListener(listener:OnApplyClickButton){
        this.listener = listener
    }
}