package com.fracta7.e_bookshelf.presentation.category_view

import com.fracta7.e_bookshelf.domain.model.Book

sealed class CategoryViewEvent{
    object LoadBooks: CategoryViewEvent()
    object ToggleEditMode: CategoryViewEvent()
}
