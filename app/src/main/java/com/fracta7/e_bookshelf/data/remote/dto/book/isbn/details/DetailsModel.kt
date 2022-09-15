package com.fracta7.e_bookshelf.data.remote.dto.book.isbn.details

import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.details.contributors.ContributorsModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.details.languages.LanguagesModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.details.table_of_content.TableOfContentsModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailsModel(
    @SerializedName("publishers")
    @Expose
    val publishers: List<String>,

    @SerializedName("number_of_pages")
    @Expose
    val number_of_pages: Int,

    @SerializedName("table_of_contents")
    @Expose
    val table_of_contents: List<TableOfContentsModel>,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("weight")
    @Expose
    val weight: String,

    @SerializedName("series")
    @Expose
    val series: List<String>,

    @SerializedName("covers")
    @Expose
    val covers: List<Int>,

    @SerializedName("physical_format")
    @Expose
    val physical_format: String,

    @SerializedName("publish_places")
    @Expose
    val publish_places: List<String>,

    @SerializedName("languages")
    @Expose
    val languages: List<LanguagesModel>,

    @SerializedName("contributors")
    @Expose
    val contributors: List<ContributorsModel>,

    @SerializedName("title")
    @Expose
    val title: String,


)