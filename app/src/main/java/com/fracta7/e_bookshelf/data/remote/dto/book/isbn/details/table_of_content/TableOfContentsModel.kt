package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.details.table_of_content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TableOfContentsModel(
    @SerializedName("level")
    @Expose
    val level: Int,

    @SerializedName("label")
    @Expose
    val label: String,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("pagenum")
    @Expose
    val pagenum: String
)
