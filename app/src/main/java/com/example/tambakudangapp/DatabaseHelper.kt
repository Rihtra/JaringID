package com.example.tambakudangapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Kelas untuk mengelola database SQLite
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "TambakUdangDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // Membuat tabel transaksi
        db.execSQL("""
            CREATE TABLE transaksi (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                jenis TEXT,
                jumlah INTEGER,
                tanggal TEXT
            )
        """)

        // Membuat tabel stok
        db.execSQL("""
            CREATE TABLE stok (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama TEXT,
                jumlah INTEGER
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Menghapus tabel jika sudah ada dan membuat tabel baru
        db.execSQL("DROP TABLE IF EXISTS transaksi")
        db.execSQL("DROP TABLE IF EXISTS stok")
        onCreate(db)
    }

    // Fungsi untuk menambahkan transaksi ke database
    fun addTransaksi(jenis: String, jumlah: Int, tanggal: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("jenis", jenis)
        values.put("jumlah", jumlah)
        values.put("tanggal", tanggal)
        db.insert("transaksi", null, values)
        db.close()
    }

    // Fungsi untuk mengambil semua data stok dari database
    fun getAllStok(): List<Stok> {
        val stokList = mutableListOf<Stok>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM stok", null)
        if (cursor.moveToFirst()) {
            do {
                val stok = Stok(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
                )
                stokList.add(stok)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return stokList
    }

    // Fungsi untuk menambahkan stok ke database
    fun addStok(nama: String, jumlah: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("nama", nama)
        values.put("jumlah", jumlah)
        db.insert("stok", null, values)
        db.close()
    }

    fun getAllTransaksi(): List<Transaksi> {
        val transaksiList = mutableListOf<Transaksi>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM transaksi", null)
        if (cursor.moveToFirst()) {
            do {
                val transaksi = Transaksi(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)
                )
                transaksiList.add(transaksi)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return transaksiList
    }
    fun updateStok(id: Int, jumlah: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("jumlah", jumlah)
        db.update("stok", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteStok(id: Int) {
        val db = this.writableDatabase
        db.delete("stok", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun updateTransaksi(id: Int, jenis: String, jumlah: Int, tanggal: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("jenis", jenis)
        values.put("jumlah", jumlah)
        values.put("tanggal", tanggal)
        db.update("transaksi", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteTransaksi(id: Int) {
        val db = this.writableDatabase
        db.delete("transaksi", "id = ?", arrayOf(id.toString()))
        db.close()
    }


}



