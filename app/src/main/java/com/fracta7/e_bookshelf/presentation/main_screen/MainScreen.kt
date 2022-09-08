package com.fracta7.e_bookshelf.presentation.main_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.data.repository.book
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainScreenViewModel>()
    var text by remember { book }
    var isbn by remember { mutableStateOf("") }
    Log.d("MainScreen", "MainScreen: ${text.title}")

    EbookshelfTheme() {
        Surface {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(4.dp)
            ) {
                OutlinedTextField(
                    value = isbn,
                    onValueChange = { isbn = it },
                    label = { Text(text = "ISBN Number", textAlign = TextAlign.Center) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = ShapeDefaults.ExtraLarge
                )
                Button(onClick = {
                    text = viewModel.triggerFlow(isbn)
                    Log.d(TAG, "MainScreen: $text")
                }) {
                    Text(text = "Load the text")
                }
                Text(text = text.title.toString(), style = MaterialTheme.typography.displayMedium, textAlign = TextAlign.Center)
                Text(text = "Author: " + text.authors)
                Text(text = "Publisher: " + text.publishers)
                Text(text = "Published date: " + text.publish_date)
                Text(text = "Number of pages: " + text.number_of_pages)
                Text(text = "Weight: " + text.weight)
            }
        }
    }
}