package com.fracta7.e_bookshelf.presentation.category_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.fracta7.e_bookshelf.presentation.destinations.BookViewDestination
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun CategoryView(navigator: DestinationsNavigator, category: String, darkTheme: Boolean = true) {
    val viewModel = hiltViewModel<CategoryViewViewModel>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    EbookshelfTheme(darkTheme = darkTheme) {
        Surface(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Text(text = category, overflow = TextOverflow.Ellipsis, maxLines = 2)
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
                        val filteredBooks = viewModel.state.books.filter { it.genre == category }
                        items(filteredBooks.size) {
                            val currentBook = filteredBooks[it]
                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = currentBook.cover)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        placeholder(R.drawable.lightning_thief)
                                        error(R.drawable.harry_potter)
                                    }).build()
                            )
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        //navigate to current book if clicked
                                        navigator.navigate(
                                            BookViewDestination(
                                                isbn = currentBook.isbn.toString(),
                                                darkTheme = darkTheme
                                            )
                                        )
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(0.40f)
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        AsyncImage(
                                            model = currentBook.cover,
                                            contentDescription = "book cover",
                                            modifier = Modifier
                                                .requiredSize(150.dp, 225.dp)
                                                .padding(12.dp)
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(0.60f)
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = currentBook.title.toString(),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Spacer(modifier = Modifier.padding(6.dp))
                                        Text(
                                            text = currentBook.authors.toString(),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.padding(4.dp))
                                        Text(
                                            text = currentBook.publish_date.toString(),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}