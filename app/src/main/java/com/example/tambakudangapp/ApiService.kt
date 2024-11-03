package com.example.tambakudangapp

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field

interface ApiService {
    @FormUrlEncoded
    @POST("add_transaksi.php")
    fun addTransaksi(
        @Field("jenis") jenis: String,
        @Field("jumlah") jumlah: Int,
        @Field("tanggal") tanggal: String
    ): Call<String>
}
