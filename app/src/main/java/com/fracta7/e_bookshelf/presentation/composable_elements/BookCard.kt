package com.fracta7.e_bookshelf.presentation.composable_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme

@Composable
fun BookCard(painter: Painter, title: String, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = ShapeDefaults.Large,
            modifier = modifier,
            elevation = CardDefaults.cardElevation(15.dp)
        ) {
            Column() {
                Image(
                    painter = painter,
                    contentDescription = "book cover",
                    modifier = Modifier.requiredSize(100.dp, 150.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BookCardPreviewLight() {
    EbookshelfTheme(darkTheme = true) {
        Surface(
            modifier = Modifier
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Library",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(28.dp)
                )
                Column() {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Fiction",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        TextButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                                contentDescription = ""
                            )
                            Text(text = "Edit", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        BookCard(
                            painter = painterResource(id = R.drawable.lightning_thief),
                            title = "Lightning Thief",
                            modifier = Modifier.padding(8.dp)
                        )
                        BookCard(
                            painter = painterResource(id = R.drawable.harry_potter),
                            title = "Harry Potter",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
