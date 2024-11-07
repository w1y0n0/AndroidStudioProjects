package com.labsi.myprodipnc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvProdi: RecyclerView
    private val list = ArrayList<Prodi>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvProdi = findViewById(R.id.rv_prodi)
        rvProdi.setHasFixedSize(true)

        list.addAll(getListProdi())
        showRecyclerList()

        val aboutPage: ImageView = findViewById(R.id.about_page)
        aboutPage.setOnClickListener {
            val intent = Intent(this, AboutMeActivity::class.java)
            startActivity(intent)
        }
    }
    private fun showRecyclerList() {
        val orientation = resources.configuration.orientation
        if (orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            rvProdi.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvProdi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        val listProdiAdapter = ListProdiAdapter(list)
        rvProdi.adapter = listProdiAdapter

        listProdiAdapter.setOnItemClickCallback(object : ListProdiAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Prodi) {
                showSelectedProdi(data)
            }
        })
    }
    private fun getListProdi(): ArrayList<Prodi> {
        val dataName = resources.getStringArray(R.array.data_nama_prodi)
        val dataJenjang = resources.getStringArray(R.array.data_jenjang)
        val dataTglBerdiri = resources.getStringArray(R.array.data_tgl_berdiri)
        val dataAkreditasi = resources.getStringArray(R.array.data_akreditasi)
        val dataSkSelenggara = resources.getStringArray(R.array.data_sk_selenggara)
        val dataTglSkSelenggara = resources.getStringArray(R.array.data_tgl_sk_selenggara)
        val dataInfoUmum = resources.getStringArray(R.array.data_informasi_umum)
        val dataIlmu = resources.getStringArray(R.array.data_ilmu_yg_dipelajari)
        val dataKompetensi = resources.getStringArray(R.array.data_kompetensi)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listProdi = ArrayList<Prodi>()
        for (position in dataName.indices) {
            val prodi = Prodi(
                dataName[position],
                dataJenjang[position],
                dataTglBerdiri[position],
                dataAkreditasi[position],
                dataSkSelenggara[position],
                dataTglSkSelenggara[position],
                dataInfoUmum[position],
                dataIlmu[position],
                dataKompetensi[position],
                dataPhoto.getResourceId(position, -1)
            )
            listProdi.add(prodi)
        }
        return listProdi
    }
    private fun showSelectedProdi(prodi: Prodi) {
        Log.d("MainActivity", "Item clicked: ${prodi.name}")
        Toast.makeText(this, "Kamu memilih " + prodi.name, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_PRODI", prodi) // Pastikan class `Prodi` implement Parcelable atau Serializable
        startActivity(intent)
    }
}