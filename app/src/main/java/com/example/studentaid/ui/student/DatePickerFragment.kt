package com.example.studentaid.ui.student

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(),DatePickerDialog.OnDateSetListener {

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       val c :Calendar = Calendar.getInstance()
        val year : Int = c.get(Calendar.YEAR)
        val month :Int = c.get(Calendar.MONTH)
        val day :Int = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day)

    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val activity :RegisterStudentActivity? = getActivity() as RegisterStudentActivity?
       // val activity: MainActivity? = getActivity() as MainActivity?
        activity?.processDatePickerResult(dayOfMonth, month, year)

    }
}