package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.covers

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CoversModel(
    @SerializedName("small")
    @Expose
    val small: String,

    @SerializedName("medium")
    @Expose
    val medium: String,

    @SerializedName("large")
    @Expose
    val large: String
)
