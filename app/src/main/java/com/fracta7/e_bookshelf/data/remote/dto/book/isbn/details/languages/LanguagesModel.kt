package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.details.languages

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LanguagesModel(
    @SerializedName("key")
    @Expose
    val key: String
)
