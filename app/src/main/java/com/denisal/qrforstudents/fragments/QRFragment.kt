package com.denisal.qrforstudents.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import com.denisal.qrforstudents.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.g0dkar.qrcode.QRCode

class QRFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_q_r, container, false)
        val textV: TextView = view.findViewById(R.id.textView)
        val textGroup: TextView = view.findViewById(R.id.textGroup)
        val mPrefs = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)!!
        val name = mPrefs.getString("fullName", "")
        val group = mPrefs.getString("group", "")
        if (name != null) {
            if (group != null) {
                val qr = "$name|$group"
                val bitmap: Bitmap = QRCode(qr).render().nativeImage() as Bitmap
                val img: ImageView = view.findViewById(R.id.QRcode)
                img.setImageBitmap(bitmap)
            }
        }
        textV.text = "ФИО студента: $name"
        textGroup.text = "Группа: $group"
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            QRFragment().apply {}
    }
}