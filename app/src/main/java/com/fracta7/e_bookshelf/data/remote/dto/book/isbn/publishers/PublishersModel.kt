package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.publishers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PublishersModel(
    @SerializedName("name")
    @Expose
    val name: String
)
