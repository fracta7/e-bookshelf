package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.identifiers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IdentifiersModel(
    @SerializedName("isbn_10")
    @Expose
    val isbn_10: List<String>,

    @SerializedName("isbn_13")
    @Expose
    val isbn_13: List<String>,

    @SerializedName("String")
    @Expose
    val lccn: List<Int>,

    @SerializedName("oclc")
    @Expose
    val oclc: List<String>
)
