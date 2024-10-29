package com.labsi.myintentapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Person(
    var name: String?,
    var age: Int?,
    var email: String?,
    var city: String?
) : Parcelable
