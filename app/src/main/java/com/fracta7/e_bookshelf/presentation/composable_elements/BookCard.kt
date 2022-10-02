package com.fracta7.e_bookshelf.presentation.composable_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.presentation.destinations.BookViewDestination
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun BookCard(url: String, title: String, modifier: Modifier, onClick: ()->Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(120.dp)) {
        Card(
            shape = ShapeDefaults.Large,
            modifier = modifier.clickable { onClick() },
            elevation = CardDefaults.cardElevation(15.dp)
        ) {
            Column() {
                AsyncImage(
                    model = url,
                    contentDescription = "book cover",
                    modifier = Modifier.requiredSize(100.dp, 150.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.image_48px),
                    error = painterResource(id = R.drawable.image_48px)
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun ExpandedBookCard(navigator: DestinationsNavigator, book: Book){
    Card(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth()
            .clickable {
                //navigate to current book if clicked
                navigator.navigate(
                    BookViewDestination(
                        isbn = book.isbn.toString()
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
                    model = book.cover,
                    contentDescription = "book cover",
                    modifier = Modifier
                        .requiredSize(150.dp, 225.dp)
                        .padding(12.dp),
                    placeholder = painterResource(id = R.drawable.image_48px),
                    error = painterResource(id = R.drawable.image_48px)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.60f)
                    .padding(8.dp)
            ) {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    text = book.authors.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = book.publish_date.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}