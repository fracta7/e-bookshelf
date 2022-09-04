package com.fracta7.e_bookshelf.domain.model.book.isbn.subjects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubjectPeopleModel(
    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("name")
    @Expose
    val name: String
)
