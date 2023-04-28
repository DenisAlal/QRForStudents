package com.denisal.qrforstudents
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.denisal.qrforstudents.databinding.ActivityHomeBinding
import com.denisal.qrforstudents.fragments.GradeFragment
import com.denisal.qrforstudents.fragments.QRFragment
import com.denisal.qrforstudents.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.container, QRFragment.newInstance()).commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.qr -> {
                    openFragment(QRFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
                R.id.grade -> {
                    openFragment(GradeFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
                R.id.settings -> {
                    openFragment(SettingsFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
    private fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }
}