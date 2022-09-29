package com.fracta7.e_bookshelf.presentation.book_view

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.presentation.composable_elements.InfoSection
import com.fracta7.e_bookshelf.presentation.destinations.EntryPointDestination
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun BookView(navigator: DestinationsNavigator, darkTheme: Boolean = true, isbn: String) {
    val viewModel = hiltViewModel<BookViewViewModel>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    LaunchedEffect(Unit) {
        viewModel.onEvent(BookViewEvent.LoadBook(isbn))
    }

    EbookshelfTheme(darkTheme = darkTheme) {
        Surface(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Text(text = viewModel.state.book?.title.toString(), overflow = TextOverflow.Ellipsis, maxLines = 2)
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
                content = {
                    AnimatedVisibility(!viewModel.state.isEditMode) {
                        LazyColumn(modifier = Modifier.padding(it)) {
                            item {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(viewModel.state.book?.cover.toString())
                                            .build(),
                                        contentDescription = "book cover",
                                        modifier = Modifier.requiredSize(200.dp, 300.dp)
                                    )
                                }
                            }
                            item {
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)) {
                                    AnimatedVisibility(
                                        viewModel.state.book?.title != ""
                                    ) {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.title_24px),
                                            label = "Title",
                                            text = viewModel.state.book?.title.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.authors != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.person_24px),
                                            label = "Authors",
                                            text = viewModel.state.book?.authors.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.publishers != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.apartment_24px),
                                            label = "Publishers",
                                            text = viewModel.state.book?.publishers.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.publish_places != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.location_on_24px),
                                            label = "Published Places",
                                            text = viewModel.state.book?.publish_places.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.publish_date != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.event_24px),
                                            label = "Published Date",
                                            text = viewModel.state.book?.publish_date.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.number_of_pages != 0) {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.numbers_24px),
                                            label = "Page Count",
                                            text = viewModel.state.book?.number_of_pages.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.weight != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.scale_24px),
                                            label = "Weight",
                                            text = viewModel.state.book?.weight.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.description != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.description_24px),
                                            label = "Description",
                                            text = viewModel.state.book?.description.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.genre != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.theater_comedy_24px),
                                            label = "Genre",
                                            text = viewModel.state.book?.genre.toString()
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.book?.readingList != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.fact_check_24px),
                                            label = "Reading List",
                                            text = viewModel.state.book?.readingList.toString()
                                        )
                                    }
                                }

                            }
                        }
                    }

                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        viewModel.onEvent(BookViewEvent.DeleteBook(viewModel.state.book?.isbn!!))
                        navigator.navigate(EntryPointDestination)
                    }) {

                    }
                }
            )
        }
    }
}