package com.example.studentaid.ui.student

import com.example.studentaid.R

abstract class ViewData{
    abstract fun getView():View
}

 class View (tvId :Int,btnID:Int){

 }

class CivilId():ViewData(){
    override fun getView(): View {
        return View(R.id.tv_face_civil_id,R.id.btn_face_civil_Id)

    }

}