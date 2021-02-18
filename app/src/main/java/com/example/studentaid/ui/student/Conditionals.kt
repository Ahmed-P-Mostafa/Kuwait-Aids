package com.example.studentaid.ui.student

import com.example.studentaid.R
import kotlinx.android.synthetic.main.activity_send_request.*

abstract class ViewData{
    abstract fun getView():View
}

 class View (tvId :Int,btnID:Int){

 }

class CivilId():ViewData(){
    override fun getView(): View {
        return View(R.id.tvCivilId,R.id.btnCivilId)

    }

}