package com.example.odontoapp.model

import androidx.annotation.DrawableRes

data class Doctor(
    val name: String,
    val specialty: String,
    val rating: Int,
    @DrawableRes val profilePictureResId: Int
)

