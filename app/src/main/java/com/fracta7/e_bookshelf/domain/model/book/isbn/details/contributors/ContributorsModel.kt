package com.fracta7.e_bookshelf.domain.model.book.isbn.details.contributors

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContributorsModel(
    @SerializedName("role")
    @Expose
    val role: String,

    @SerializedName("name")
    @Expose
    val name: String
)
