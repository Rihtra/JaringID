package com.example.tambakudangapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TransaksiFragment : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var apiService: ApiService
    private lateinit var txtTransaksi: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaksi, container, false)
        dbHelper = DatabaseHelper(requireContext())

        // Inisialisasi Retrofit dan ApiService
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost/tambakudangapp/") // Ganti dengan URL server yang benar
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Referensi UI
        val inputJenis = view.findViewById<EditText>(R.id.inputJenis)
        val inputJumlah = view.findViewById<EditText>(R.id.inputJumlah)
        val inputTanggal = view.findViewById<EditText>(R.id.inputTanggal)
        val btnTambahTransaksi = view.findViewById<Button>(R.id.btnTambahTransaksi)
        val txtTransaksi = view.findViewById<TextView>(R.id.txtTransaksi)

        // Tambahkan Data Transaksi
        btnTambahTransaksi.setOnClickListener {
            val jenis = inputJenis.text.toString()
            val jumlah = inputJumlah.text.toString().toInt()
            val tanggal = inputTanggal.text.toString()

            // Memanggil API untuk menambahkan transaksi
            addTransaksiToApi(jenis, jumlah, tanggal)

            // Kosongkan input
            inputJenis.text.clear()
            inputJumlah.text.clear()
            inputTanggal.text.clear()
        }

        // Update tampilan transaksi
        updateTransaksiDisplay(txtTransaksi)

        return view
    }

    private fun addTransaksiToApi(jenis: String, jumlah: Int, tanggal: String) {
        val call = apiService.addTransaksi(jenis, jumlah, tanggal)
        call.enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                if (response.isSuccessful) {
                    // Transaksi berhasil ditambahkan
                    updateTransaksiDisplay(txtTransaksi)
                } else {
                    // Tangani kesalahan respons
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Tangani kesalahan jaringan
            }
        })
    }

    private fun updateTransaksiDisplay(txtTransaksi: TextView) {
        val transaksiList = dbHelper.getAllTransaksi()
        val transaksiText = transaksiList.joinToString("\n") { "${it.jenis}: ${it.jumlah} pada ${it.tanggal}" }
        txtTransaksi.text = transaksiText
    }
}
