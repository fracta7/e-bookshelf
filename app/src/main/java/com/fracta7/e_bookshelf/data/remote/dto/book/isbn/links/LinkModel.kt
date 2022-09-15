package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.links

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LinkModel(
    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("title")
    @Expose
    val title: String
)