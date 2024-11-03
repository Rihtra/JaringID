package com.example.tambakudangapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class StokFragment: Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stok, container, false)
        dbHelper = DatabaseHelper(requireContext())

        // Referensi UI
        val inputNamaStok = view.findViewById<EditText>(R.id.inputNamaStok)
        val inputJumlahStok = view.findViewById<EditText>(R.id.inputJumlahStok)
        val btnTambahStok = view.findViewById<Button>(R.id.btnTambahStok)
        val txtStok = view.findViewById<TextView>(R.id.txtStok)

        // Tambahkan Stok Baru
        btnTambahStok.setOnClickListener {
            val namaStok = inputNamaStok.text.toString()
            val jumlahStok = inputJumlahStok.text.toString().toInt()

            dbHelper.addStok(namaStok, jumlahStok)

            // Kosongkan input
            inputNamaStok.text.clear()
            inputJumlahStok.text.clear()
            updateStokDisplay(txtStok)
        }

        // Update tampilan stok
        updateStokDisplay(txtStok)

        return view
    }

    private fun updateStokDisplay(txtStok: TextView) {
        val stokList = dbHelper.getAllStok()
        val stokText = stokList.joinToString("\n") { "${it.nama}: ${it.jumlah}" }
        txtStok.text = stokText
    }
}
