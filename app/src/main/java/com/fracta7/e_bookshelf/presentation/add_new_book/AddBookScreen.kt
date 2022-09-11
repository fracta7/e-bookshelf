package com.fracta7.e_bookshelf.presentation.add_new_book

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen() {
    var isbn by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Done") }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var publisher by remember { mutableStateOf("") }
    var publish_date by remember { mutableStateOf("") }
    var number_of_pages by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column() {
        Text(
            text = "ADD A NEW BOOK",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(12.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = ShapeDefaults.ExtraLarge
        ) {
            Text(
                text = "ADD BY ISBN NUMBER",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = isbn,
                    onValueChange = { isbn = it },
                    shape = ShapeDefaults.Large,
                    label = { Text(text = "ISBN") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = status,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(12.dp),
                    color = Color(0xFF4CAF50)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = ShapeDefaults.ExtraLarge
        ) {
            Column(Modifier.padding(12.dp)) {
                Text(
                    text = "ADD MANUALLY",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "TITLE") },
                    singleLine = true,
                    shape = ShapeDefaults.Large
                )
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text(text = "AUTHOR") },
                    singleLine = true,
                    shape = ShapeDefaults.Large
                )
                OutlinedTextField(
                    value = publisher,
                    onValueChange = { publisher = it },
                    label = { Text(text = "PUBLISHER") },
                    singleLine = true,
                    shape = ShapeDefaults.Large
                )
                //todo date picker
                OutlinedTextField(
                    value = number_of_pages,
                    onValueChange = { number_of_pages = it },
                    shape = ShapeDefaults.Large,
                    label = { Text(text = "PAGE COUNT") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                //todo category selector

            }

        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun AddBookPrev() {
    EbookshelfTheme(darkTheme = true) {
        Surface {
            AddBookScreen()
        }
    }
}