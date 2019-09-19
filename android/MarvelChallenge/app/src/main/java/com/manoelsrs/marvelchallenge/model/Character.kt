package com.manoelsrs.marvelchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val photo: String,
    val photoExtension: String
) : Parcelable