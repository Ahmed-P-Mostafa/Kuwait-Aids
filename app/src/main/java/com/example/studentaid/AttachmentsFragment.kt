package com.example.studentaid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.studentaid.adapters.AttachmentsAdapter
import kotlinx.android.synthetic.main.fragment_attachments.*
import kotlinx.android.synthetic.main.fragment_attachments.view.*
import me.relex.circleindicator.CircleIndicator2


class AttachmentsFragment : Fragment() {

    val args :AttachmentsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attachments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AttachmentsAdapter(args.student.documentList)
        rv_attachments.adapter =adapter


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv_attachments)


        indicator.attachToRecyclerView(rv_attachments,snapHelper)
    }


}