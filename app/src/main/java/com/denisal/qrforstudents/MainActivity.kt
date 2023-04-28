package com.denisal.qrforstudents

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val generateQR: Button = findViewById(R.id.generateQR)
        val fio: TextView = findViewById(R.id.fullName)
        val studGroup: TextView = findViewById(R.id.studGroup)
        generateQR.setOnClickListener {
            if (fio.text.isNotEmpty() && studGroup.text.isNotEmpty()) {
                if (checkStud()) {
                    val intent = Intent(this@MainActivity,HomeActivity::class.java)
                    startActivity(intent)
                    this.finish()
                } else {
                    Toast.makeText(applicationContext, "Данного студента нет в базе данных", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "Заполните все поля!", Toast.LENGTH_LONG).show()
            }

        }
        val info: FloatingActionButton = findViewById(R.id.goInfo)
        info.setOnClickListener {
            val builderSucceed = AlertDialog.Builder(this)
                .setTitle("Информация")
                .setMessage(
                    "Если у вас возникает ошибка при входе, то скорее всего вы пишете в неправильном формате," +
                            " или вас не добавил еще не один преподаватель в этом семестре!\nПодсказка:\n " +
                            "1. Проверьте правильно ли написана ваша ФИО\n " +
                            "2. Проверьте правильно ли вы написали группу, она должна соответствовать формату\n" +
                            "3. Проверьте нет ли лишних пробелов, между фио должно быть по одному пробелу, " +
                            "до и после их быть не должно"
                )
            builderSucceed.setPositiveButton("OK") { _, _ ->
            }
            val alertDialogSuccess: AlertDialog = builderSucceed.create()
            alertDialogSuccess.show()
        }


    }

    private fun checkStud(): Boolean {
        val fio: TextView = findViewById(R.id.fullName)
        val studGroup: TextView = findViewById(R.id.studGroup)
        var check = false
        var cn: Connection
        thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                cn = DriverManager.getConnection(url, user, pass)
                val ps = cn.createStatement()
                val resultSet = ps!!.executeQuery(
                    "SELECT studGroup, fullName FROM " +
                            "student WHERE fullName = '${fio.text}' AND studGroup = '${studGroup.text}' "
                )
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
        val group = mPrefs.getString("group", "")
        if (name != null && group != null) {
            if (name.isNotEmpty()) {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }
    }
}