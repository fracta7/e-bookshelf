package com.fracta7.e_bookshelf.presentation.add_new_book

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Genres
import com.fracta7.e_bookshelf.presentation.destinations.EntryPointDestination
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = AddBookAnimations::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedVisibilityScope.AddBookScreen(
    navigator: DestinationsNavigator,
    darkTheme: Boolean = true
) {
    val viewModel = hiltViewModel<AddBookViewModel>()
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
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val icon = if (viewModel.state.isBookAvailable) {
        Icons.Filled.KeyboardArrowDown
    } else {
        Icons.Filled.KeyboardArrowUp
    }
    EbookshelfTheme(darkTheme = darkTheme) {
        Surface() {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Text(
                                text = "ADD A NEW BOOK",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navigator.navigate(EntryPointDestination)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                    contentDescription = "",
                                    modifier = Modifier.requiredSize(32.dp)
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { viewModel.onEvent(AddBookEvent.AddBook) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_add_24),
                            contentDescription = null
                        )
                    }
                }
            ) {
                LazyColumn(
                    modifier = Modifier.padding(it)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = ShapeDefaults.ExtraLarge,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
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

                        TextButton(onClick = { viewModel.onEvent(AddBookEvent.ToggleState) }) {
                            Icon(icon, contentDescription = null)
                            Text(text = "ADD MANUALLY")
                        }
                    }
                    if (!viewModel.state.isBookAvailable) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = ShapeDefaults.ExtraLarge,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
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
                                }

                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = ShapeDefaults.ExtraLarge,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(
                                        text = "SELECT A CATEGORY",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(start = 16.dp, top = 12.dp)
                                    )

                                    //dropdown menu
                                    var expanded by remember { mutableStateOf(false) }
                                    var selectedItem by remember { mutableStateOf("") }
                                    var textFiledSize by remember { mutableStateOf(Size.Zero) }
                                    val icon = if (expanded) {
                                        Icons.Filled.KeyboardArrowUp
                                    } else {
                                        Icons.Filled.KeyboardArrowDown
                                    }
                                    Column() {
                                        OutlinedTextField(
                                            value = selectedItem,
                                            onValueChange = { selectedItem = it },
                                            modifier = Modifier
                                                .clickable {
                                                    expanded = !expanded
                                                }
                                                .onGloballyPositioned { coordinates ->
                                                    textFiledSize = coordinates.size.toSize()
                                                },
                                            label = { Text(text = "SELECT") },
                                            trailingIcon = {
                                                Icon(
                                                    icon,
                                                    contentDescription = null,
                                                    modifier = Modifier.clickable {
                                                        expanded = !expanded
                                                    })
                                            },
                                            enabled = false,
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                disabledBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                                disabledLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                                disabledTextColor = MaterialTheme.colorScheme.onPrimaryContainer
                                            ),
                                            shape = ShapeDefaults.Medium
                                        )

                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = {
                                                expanded = false
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(0.93f)
                                                .height(250.dp)
                                        ) {
                                            LazyColumn(
                                                modifier = Modifier.size(
                                                    width = with(LocalDensity.current) { textFiledSize.width.toDp() },
                                                    height = 250.dp
                                                )
                                            ) {
                                                items(Genres.list.size) {
                                                    DropdownMenuItem(
                                                        text = { Text(text = Genres.list[it]) },
                                                        onClick = {
                                                            selectedItem = Genres.list[it]
                                                            expanded = false
                                                            viewModel.onEvent(
                                                                AddBookEvent.SelectCategory(
                                                                    selectedItem
                                                                )
                                                            )
                                                        })
                                                }
                                            }
                                        }
                                    }
                                }
                                Text(text = viewModel.state.selectedCategory)
                            }
                        }
                    }
                }
            }
        }
    }
}