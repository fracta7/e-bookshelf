package com.fracta7.e_bookshelf.presentation.reading_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.presentation.composable_elements.ExpandedBookCard
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ReadingListView(navigator: DestinationsNavigator, readingList: String) {
    val viewModel = hiltViewModel<ReadingListAllViewModel>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    EbookshelfTheme(
        darkTheme = viewModel.state.darkTheme,
        dynamicColor = viewModel.state.dynamicTheme
    ) {
        Surface(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Text(text = readingList, overflow = TextOverflow.Ellipsis, maxLines = 2)
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navigator.navigateUp()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_back_24px),
                                    contentDescription = "Back"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                content = { it ->
                    LazyColumn(modifier = Modifier.padding(it)) {
                        val filteredBooks =
                            viewModel.state.books.filter { it.readingList == readingList }

                        items(filteredBooks.size) {
                            val currentBook = filteredBooks[it]
                            ExpandedBookCard(navigator = navigator, book = currentBook)
                        }
                    }
                }
            )
        }
    }
}