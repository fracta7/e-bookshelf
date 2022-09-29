package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.publishers

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class PublishPlacesModel(
    @SerializedName("name")
    @Expose
    val name: String
)
