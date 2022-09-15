package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.subjects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubjectPlacesModel(
    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("name")
    @Expose
    val name: String
)