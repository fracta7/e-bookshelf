package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.ebooks

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class EbooksModel(
    @SerializedName("preview_url")
    @Expose
    val preview_url: String
)
