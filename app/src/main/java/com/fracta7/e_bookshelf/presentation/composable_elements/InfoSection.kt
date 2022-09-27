package com.fracta7.e_bookshelf.presentation.composable_elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun InfoSection(
    painter: Painter,
    modifier: Modifier = Modifier,
    circleColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    tintColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    label: String,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            painter = painter,
            contentDescription = "info icon",
            modifier = Modifier
                .drawBehind {
                    drawCircle(color = circleColor, radius = size.width / 1.5f)
                },
            tint = tintColor
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = label, style = MaterialTheme.typography.labelMedium)
            Text(text = text, style = MaterialTheme.typography.labelSmall)
        }
    }
}