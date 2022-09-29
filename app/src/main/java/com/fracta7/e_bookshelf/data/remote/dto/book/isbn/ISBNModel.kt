package com.fracta7.e_bookshelf.data.remote.dto.book.isbn

import androidx.annotation.Keep
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.authors.AuthorsModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.classifications.ClassificationsModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.covers.CoversModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.ebooks.EbooksModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.excerpts.ExcerptsModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.identifiers.IdentifiersModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.links.LinkModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.publishers.PublishPlacesModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.publishers.PublishersModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.subjects.SubjectPeopleModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.subjects.SubjectPlacesModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.subjects.SubjectTimeModel
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.subjects.SubjectsModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ISBNModel(
    @SerializedName("bib_key")
    @Expose
    val bib_key: String,

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("authors")
    @Expose
    val authors: List<AuthorsModel>,

    @SerializedName("identifiers")
    @Expose
    val identifiers: IdentifiersModel,

    @SerializedName("classifications")
    @Expose
    val classifications: ClassificationsModel,

    @SerializedName("subjects")
    @Expose
    val subjects: List<SubjectsModel>,

    @SerializedName("subjects_places")
    @Expose
    val subject_places: List<SubjectPlacesModel>,

    @SerializedName("subject_people")
    @Expose
    val subject_people: List<SubjectPeopleModel>,

    @SerializedName("subject_time")
    @Expose
    val subject_time: List<SubjectTimeModel>,

    @SerializedName("publishers")
    @Expose
    val publishers: List<PublishersModel>,

    @SerializedName("publish_places")
    @Expose
    val publish_places: List<PublishPlacesModel>,

    @SerializedName("publish_date")
    @Expose
    val publish_date: String,

    @SerializedName("excerpts")
    @Expose
    val excerpts: List<ExcerptsModel>,

    @SerializedName("links")
    @Expose
    val links: List<LinkModel>,

    @SerializedName("cover")
    @Expose
    val cover: CoversModel,

    @SerializedName("ebooks")
    @Expose
    val ebooks: List<EbooksModel>,

    @SerializedName("number_of_pages")
    @Expose
    val number_of_pages: Int,

    @SerializedName("weight")
    @Expose
    val weight: String
)