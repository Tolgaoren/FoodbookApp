package com.toren.foodbookapp.model

data class Yemek(
    val yemekIsmi: String = "",
    val aciklama: String = "",
    val malzemeler: ArrayList<String> = arrayListOf(),
    val malzemelerDetayli: ArrayList<String> = arrayListOf(),
    val hazirlanis: String = "",
    val kategori: String = "",
    val imgUrl: String = "",
    val user: String = ""
)
