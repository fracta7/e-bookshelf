package com.fracta7.e_bookshelf.data.mapper

import com.fracta7.e_bookshelf.data.local.database.book.ReadingListEntity
import com.fracta7.e_bookshelf.domain.model.ReadingList

fun ReadingListEntity.toReadingList(): ReadingList{
    return ReadingList(
        name = name,
        isDone = isDone
    )
}

fun ReadingList.toReadingListEntity(): ReadingListEntity{
    return ReadingListEntity(
        name = name,
        isDone = isDone
    )
}