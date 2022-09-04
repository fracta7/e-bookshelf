package com.fracta7.e_bookshelf.domain.model.book.isbn.authors

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthorsModel(
    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("name")
    @Expose
    val name: String
)
