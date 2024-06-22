package com.example.housepricepredict

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Tombol Kembali
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Tombol Tentang Aplikasi
        val btnTentangAplikasi = findViewById<Button>(R.id.about)
        btnTentangAplikasi.setOnClickListener {
            val intent = Intent(this, TentangAplikasiActivity::class.java)
            startActivity(intent)
        }

        // Tombol Dataset
        val btnDataset = findViewById<Button>(R.id.dataset)
        btnDataset.setOnClickListener {
            val intent = Intent(this, DatasetActivity::class.java)
            startActivity(intent)
        }

        // Tombol Fitur
        val btnFitur = findViewById<Button>(R.id.fitur)
        btnFitur.setOnClickListener {
            val intent = Intent(this, FiturActivity::class.java)
            startActivity(intent)
        }

        // Tombol Model
        val btnModel = findViewById<Button>(R.id.model)
        btnModel.setOnClickListener {
            val intent = Intent(this, ModelActivity::class.java)
            startActivity(intent)
        }
        // Tombol Simulasi Model
        val btnSIModel = findViewById<Button>(R.id.simulasimodel)
        btnSIModel.setOnClickListener {
            val intent = Intent(this, SIModelActivity::class.java)
            startActivity(intent)
        }

    }
}
