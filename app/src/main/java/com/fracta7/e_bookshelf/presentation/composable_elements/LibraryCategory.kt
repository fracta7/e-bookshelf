package com.fracta7.e_bookshelf.presentation.composable_elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.presentation.destinations.BookViewDestination
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun LibraryCategory(
    title: String,
    books: List<Book>,
    modifier: Modifier,
    navigator: DestinationsNavigator,
    onClick: ()->Unit
) {
    Card(
        modifier = Modifier.padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
        shape = ShapeDefaults.ExtraLarge
    ) {
        Column(modifier = modifier) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 12.dp)
                )
                FilledTonalIconButton(
                    onClick = { onClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.navigate_next_24px),
                        contentDescription = ""
                    )
                }
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                items(books.size) {
                    BookCard(
                        url = books[it].cover.toString(),
                        title = books[it].title.toString(),
                        modifier = Modifier.padding(12.dp),
                        onClick = { navigator.navigate(BookViewDestination(isbn = books[it].isbn.toString())) }
                    )
                }
            }
        }
    }
}


