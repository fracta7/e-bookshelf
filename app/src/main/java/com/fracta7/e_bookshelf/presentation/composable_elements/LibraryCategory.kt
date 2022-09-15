package com.fracta7.e_bookshelf.presentation.composable_elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme

val sampleBooks = listOf(
    Book(
        "",
        "",
        "Lightning Thief",
        "Rick Riordan",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Harry Potter",
        "J.K. Rowling",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Lightning Thief",
        "Rick Riordan",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Harry Potter",
        "J.K. Rowling",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Lightning Thief",
        "Rick Riordan",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Harry Potter",
        "J.K. Rowling",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Lightning Thief",
        "Rick Riordan",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Harry Potter",
        "J.K. Rowling",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Lightning Thief",
        "Rick Riordan",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Harry Potter",
        "J.K. Rowling",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Lightning Thief",
        "Rick Riordan",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
    Book(
        "",
        "",
        "Harry Potter",
        "J.K. Rowling",
        "",
        "",
        "",
        "",
        0,
        "",
        "Default",
        "",
        false
    ),
)

val sampleCategories = listOf(
    "Fiction",
    "SciFi",
    "Romance",
    "Drama",
    "Science"
)

@Composable
fun LibraryCategory(title: String, books: List<Book>, modifier: Modifier) {
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
                FilledTonalIconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                        contentDescription = ""
                    )
                }
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(books.size) { it ->
                    BookCard(
                        painter = painterResource(id = R.drawable.lightning_thief),
                        title = books[it].title!!,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryPrevi() {
    EbookshelfTheme() {
        Surface() {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Library",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(28.dp)
                )
                LazyColumn {
                    items(sampleCategories.size) {
                        LibraryCategory(
                            title = sampleCategories[it],
                            books = sampleBooks,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

