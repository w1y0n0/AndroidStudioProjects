package com.labsi.myprodipnc

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Prodi(
    val name: String,
    val jenjang: String,
    val tglBerdiri: String,
    val akreditasi: String,
    val skSelenggara: String,
    val tglSkSelenggara: String,
    val infoUmum: String,
    val ilmu: String,
    val kompetensi: String,
    val photo: Int
) : Parcelable