package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.classifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ClassificationsModel(
    @SerializedName("lc_classifications")
    @Expose
    val lc_classifications: List<String>,

    @SerializedName("dewey_decimal_class")
    @Expose
    val dewey_decimal_class: List<String>
)
