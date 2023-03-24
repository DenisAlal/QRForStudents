package com.denisal.qrforstudents
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.g0dkar.qrcode.QRCode


class QRGenerateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrgenerate)
        val textV: TextView = findViewById(R.id.textView)
        val mPrefs = getSharedPreferences("data", 0)
        val name = mPrefs.getString("fullName", "")
        val group = mPrefs.getString("group", "")
        if (name != null) {
            if (group != null) {
                val qr = "$name|$group"
                val bitmap: Bitmap = QRCode(qr).render().nativeImage() as Bitmap
                val img: ImageView = findViewById(R.id.QRcode)
                img.setImageBitmap(bitmap)
            }
        }
        textV.text = "ФИО студента: $name"
    }
}