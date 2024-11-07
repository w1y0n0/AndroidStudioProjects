package com.labsi.myprodipnc

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prodi: Prodi? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_PRODI", Prodi::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("EXTRA_PRODI")
        }


        val ivProdiDetail: ImageView = findViewById(R.id.iv_prodi_detail)
        val tvItemName: TextView = findViewById(R.id.tv_item_nama_prodi)
        val tvItemJenjang: TextView = findViewById(R.id.tv_item_jenjang)
        val tvItemTglBerdiri: TextView = findViewById(R.id.tv_item_tgl_berdiri)
        val tvItemSkSelenggara: TextView = findViewById(R.id.tv_item_sk_selenggara)
        val tvItemAkreditasi: TextView = findViewById(R.id.tv_item_akreditasi)
        val tvInformasiUmum: TextView = findViewById(R.id.tv_informasi_umum)
        val tvIlmuYangDipelajari: TextView = findViewById(R.id.tv_ilmu_yg_dipelajari)
        val tvKompetensi: TextView = findViewById(R.id.tv_kompetensi)

        ivProdiDetail.setImageResource(prodi?.photo ?: 0)
        tvItemName.text = prodi?.name ?: ""
        tvItemJenjang.text = prodi?.jenjang ?: ""
        tvItemTglBerdiri.text = prodi?.tglBerdiri ?: ""
        tvItemSkSelenggara.text = prodi?.skSelenggara ?: ""
        tvItemAkreditasi.text = prodi?.akreditasi ?: ""
        tvInformasiUmum.text = prodi?.infoUmum ?: ""
        tvIlmuYangDipelajari.text = prodi?.ilmu ?: ""
        tvKompetensi.text = prodi?.kompetensi ?: ""

        val btnBack: ImageView = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val fabShare: FloatingActionButton = findViewById(R.id.action_share)
        fabShare.setOnClickListener {
            shareItem(prodi)
            true
        }
    }

    private fun shareItem(prodi: Prodi?) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Lihat Program Studi Ini!")
            putExtra(
                Intent.EXTRA_TEXT,
                """Program Studi ${prodi?.name} jenjang ${prodi?.jenjang} Politeknik Negeri Cilacap. Link IG pmbpnc: https://www.instagram.com/pmbpnc/""".trimIndent()
            )
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}