package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.authors

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class AuthorsModel(
    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("name")
    @Expose
    val name: String
)
