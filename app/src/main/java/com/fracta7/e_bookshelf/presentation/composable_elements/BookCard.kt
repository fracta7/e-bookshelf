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
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme

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
                    contentScale = ContentScale.Crop
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