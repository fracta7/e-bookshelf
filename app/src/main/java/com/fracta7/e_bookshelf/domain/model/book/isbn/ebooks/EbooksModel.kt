package com.fracta7.e_bookshelf.domain.model.book.isbn.ebooks

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EbooksModel(
    @SerializedName("preview_url")
    @Expose
    val preview_url: String
)
