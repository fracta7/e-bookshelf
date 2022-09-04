package com.fracta7.e_bookshelf.domain.model.book.isbn.excerpts

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExcerptsModel(
    @SerializedName("comment")
    @Expose
    val comment: String,

    @SerializedName("text")
    @Expose
    val text: String
)
