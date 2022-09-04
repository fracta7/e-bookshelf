package com.fracta7.e_bookshelf.domain.model.book.isbn.publishers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PublishPlacesModel(
    @SerializedName("name")
    @Expose
    val name: String
)
