package com.denisal.qrforstudents.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import com.denisal.qrforstudents.MainActivity
import com.denisal.qrforstudents.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.processphoenix.ProcessPhoenix

class SettingsFragment : Fragment() {
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_settings, container, false)
        val quit: AppCompatButton = view.findViewById(R.id.quit)
        quit.setOnClickListener{
            val mPrefs = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)!!
            mPrefs.edit().remove("fullName").apply()
            mPrefs.edit().remove("group").apply()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        val theme: SwitchCompat = view.findViewById(R.id.switchTheme)
        sharedPref = activity?.getSharedPreferences("THEME", Context.MODE_PRIVATE)!!
        val editor: SharedPreferences.Editor = sharedPref.edit()
        val nightMode = sharedPref.getBoolean("nightMode", false)
        if (nightMode) {
            theme.isChecked = true
        }
        theme.setOnClickListener{
            if (theme.isChecked) {
                editor.putBoolean("nightMode", true).apply()
                ProcessPhoenix.triggerRebirth(context)
            } else {
                editor.putBoolean("nightMode", false).apply()
                ProcessPhoenix.triggerRebirth(context)
            }
        }

        return view
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}