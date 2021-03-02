package com.example.studentaid.ui.ministryEmployee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.studentaid.R
import com.example.studentaid.adapters.AttachmentsAdapter
import kotlinx.android.synthetic.main.fragment_attachments.*
import kotlinx.android.synthetic.main.fragment_attachments.indicator
import kotlinx.android.synthetic.main.fragment_documents.*
import me.relex.circleindicator.CircleIndicator2


class DocumentsFragment : Fragment() {
    val args:DocumentsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_documents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AttachmentsAdapter(args.student.documentList)
        rv_documents.adapter =adapter


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv_documents)


        indicator2.attachToRecyclerView(rv_documents,snapHelper)
    }


}