package com.fracta7.e_bookshelf.presentation.reading_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReadingListAll(title: String, bookCount: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
        shape = ShapeDefaults.ExtraLarge
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(12.dp)
            )
            Text(
                text = "Number of Books: $bookCount",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}