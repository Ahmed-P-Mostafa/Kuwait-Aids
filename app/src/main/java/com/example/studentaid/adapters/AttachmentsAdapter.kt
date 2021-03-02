package com.example.studentaid.adapters

import android.media.Image
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.studentaid.data.models.Document
import com.example.studentaid.databinding.AttachmentLayoutBinding


class AttachmentsAdapter(val list:List<Document>?):RecyclerView.Adapter<AttachmentsAdapter.ViewHolder>() {
    private val TAG = "AttachmentsAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AttachmentLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(list?.get(position)!!)

    }

    override fun getItemCount()= list?.size?:0

    fun ImageView.load(uri:String){
        Glide.with(this).load(uri).into(this)
    }
    class ViewHolder(var binding:AttachmentLayoutBinding) :RecyclerView.ViewHolder(binding.root){

       fun bind(item:Document){
           Log.d("AttachmentsAdapter", "bind: ${item.url} ")

              Glide.with(binding.imageView).load(item.url).centerInside().into(binding.imageView)
               binding.textView.setText(item.name)


       }

    }

}