package com.fracta7.e_bookshelf.domain.model.book

import com.fracta7.e_bookshelf.domain.model.book.isbn.ISBNModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JSONModel(
    val isbn: Map<String, ISBNModel>
)