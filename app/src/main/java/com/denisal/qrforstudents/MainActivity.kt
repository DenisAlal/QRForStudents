package com.denisal.qrforstudents

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val generateQR: Button = findViewById(R.id.generateQR)
        generateQR.setOnClickListener {
            if (checkStud()) {
                val intent = Intent(this@MainActivity,QRGenerateActivity::class.java)
                startActivity(intent)
                this.finish()
            } else {
                Toast.makeText(applicationContext, "Данного студента нет в базе данных", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkStud(): Boolean {
        val fn: TextView = findViewById(R.id.fullName)
        var check = false
        var cn: Connection
        thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                cn = DriverManager.getConnection(url, user, pass)
                val ps = cn.createStatement()
                val resultSet = ps!!.executeQuery("SELECT `studGroup`, `fullName` FROM " +
                        "`student` WHERE `fullName` = '${fn.text}'")
                while (resultSet.next()) {
                    check = true
                    val n = resultSet.getString("fullName")
                    val g = resultSet.getString("studGroup")
                    save(n, g)
                    break
                }
                ps.close()
                cn.close()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }.join()
        return check
    }
    private fun save(fName: String, group: String) {
        val mPrefs: SharedPreferences = getSharedPreferences("data", 0)
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.putString("fullName", fName)
        editor.putString("group", group)
        editor.apply()
    }
    override fun onStart() {
        super.onStart()
        val mPrefs = getSharedPreferences("data", 0)
        val name = mPrefs.getString("fullName", "")
        if (name != null) {
            if(name.isNotEmpty()) {
                val intent = Intent(this@MainActivity,QRGenerateActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }
    }
}