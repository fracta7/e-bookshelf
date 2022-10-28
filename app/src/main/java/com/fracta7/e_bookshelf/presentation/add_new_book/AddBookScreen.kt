package com.fracta7.e_bookshelf.presentation.add_new_book

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Genres
import com.fracta7.e_bookshelf.presentation.composable_elements.InfoSection
import com.fracta7.e_bookshelf.presentation.destinations.EntryPointDestination
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun AddBookScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<AddBookViewModel>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    EbookshelfTheme(
        darkTheme = viewModel.state.darkTheme,
        dynamicColor = viewModel.state.dynamicTheme
    ) {
        Surface(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Text(
                                text = "Add a new book",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navigator.navigateUp()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_back_24px),
                                    contentDescription = "",
                                    modifier = Modifier.requiredSize(32.dp)
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        viewModel.onEvent(AddBookEvent.AddBook)
                        navigator.navigate(EntryPointDestination)
                    }) {
                        Text(text = "Add")
                    }
                }
            ) { it1 ->
                LazyColumn(
                    contentPadding = it1,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = ShapeDefaults.ExtraLarge,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                        ) {
                            Text(
                                text = "Add by ISBN",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
                            )
                            val statusColor = when (viewModel.state.status) {
                                "Found" -> {
                                    Color(0xFF4CAF50)
                                }
                                "No result" -> {
                                    MaterialTheme.colorScheme.error
                                }
                                "Loading..." -> {
                                    Color(0xFFCCB832)
                                }
                                else -> {
                                    Color(0xFF4CAF50)
                                }
                            }
                            OutlinedTextField(
                                value = viewModel.state.isbn,
                                onValueChange = {
                                    viewModel.state = viewModel.state.copy(isbn = it)
                                    viewModel.onEvent(AddBookEvent.CheckBook(viewModel.state.isbn))
                                },
                                shape = ShapeDefaults.Large,
                                label = { Text(text = "ISBN") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.barcode_24px),
                                        contentDescription = null
                                    )
                                }
                            )
                            Text(
                                text = viewModel.state.status,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 16.dp),
                                color = statusColor
                            )

                            //dropdown menu
                            var expanded by remember { mutableStateOf(false) }
                            var selectedItem by remember { mutableStateOf("") }
                            var textFilledSize0 by remember { mutableStateOf(Size.Zero) }
                            val icon = if (expanded) {
                                Icons.Filled.KeyboardArrowUp
                            } else {
                                Icons.Filled.KeyboardArrowDown
                            }
                            val selectColor = if (expanded) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outline
                            }

                            //genre selector
                            OutlinedTextField(
                                value = selectedItem,
                                onValueChange = { selectedItem = it },
                                modifier = Modifier
                                    .clickable {
                                        expanded = !expanded
                                    }
                                    .onGloballyPositioned { coordinates ->
                                        textFilledSize0 = coordinates.size.toSize()
                                    }
                                    .padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                                    .fillMaxWidth(),
                                label = { Text(text = "Select the genre") },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.theater_comedy_24px),
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        icon,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            expanded = !expanded
                                        })
                                },
                                readOnly = true,
                                shape = ShapeDefaults.Large
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                },
                                offset = DpOffset(64.dp, 12.dp)
                            ) {
                                LazyColumn(
                                    modifier = Modifier.size(
                                        width = 220.dp,
                                        height = 500.dp
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

                            //reading list
                            var expandedReadingList by remember { mutableStateOf(false) }
                            var textFilledSize1 by remember { mutableStateOf(Size.Zero) }
                            var selectedReadingList by remember { mutableStateOf("") }
                            val iconReadingList = if (expandedReadingList) {
                                Icons.Filled.KeyboardArrowUp
                            } else {
                                Icons.Filled.KeyboardArrowDown
                            }
                            OutlinedTextField(
                                value = selectedItem,
                                onValueChange = { selectedItem = it },
                                modifier = Modifier
                                    .clickable {
                                        expandedReadingList = !expandedReadingList
                                    }
                                    .onGloballyPositioned { coordinates ->
                                        textFilledSize1 = coordinates.size.toSize()
                                    }
                                    .padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.fact_check_24px),
                                        contentDescription = "reading list"
                                    )
                                },
                                trailingIcon = {
                                    Icon(iconReadingList, contentDescription = null,
                                        modifier = Modifier.clickable {
                                            expandedReadingList = !expandedReadingList
                                        })
                                },
                                readOnly = true,
                                shape = ShapeDefaults.Large
                            )
                            DropdownMenu(
                                expanded = expandedReadingList,
                                onDismissRequest = {
                                    expandedReadingList = false
                                },
                                offset = DpOffset(64.dp, 12.dp)
                            ) {
                                LazyColumn(
                                    modifier = Modifier.size(
                                        width = 220.dp,
                                        height = 500.dp
                                    )
                                ) {
                                    item {
                                        DropdownMenuItem(
                                            text = {
                                                Text(text = "Default")
                                            },
                                            onClick = {
                                                selectedReadingList = "Default"
                                                expandedReadingList = false
                                                viewModel.onEvent(
                                                    AddBookEvent.SelectReadingList(
                                                        selectedReadingList
                                                    )
                                                )
                                            })
                                    }
                                    if (viewModel.state.readingList.isNotEmpty()) {
                                        items(viewModel.state.readingList.size) {
                                            DropdownMenuItem(
                                                text = {
                                                    viewModel.state.readingList[it].name?.let { it2 ->
                                                        Text(
                                                            text = it2
                                                        )
                                                    }
                                                },
                                                onClick = {
                                                    selectedReadingList =
                                                        viewModel.state.readingList[it].name.toString()
                                                    expandedReadingList = false
                                                    viewModel.onEvent(
                                                        AddBookEvent.SelectReadingList(
                                                            selectedReadingList
                                                        )
                                                    )
                                                })
                                        }
                                    }
                                }
                            }

                            //description
                            OutlinedTextField(
                                value = viewModel.state.description,
                                onValueChange = {
                                    viewModel.state = viewModel.state.copy(description = it)
                                },
                                label = { Text("Description (optional)") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, bottom = 12.dp, end = 12.dp),
                                shape = ShapeDefaults.Large,
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.description_24px),
                                        contentDescription = null
                                    )
                                }
                            )

                            //animate info section depending on the available info individually
                            AnimatedVisibility(viewModel.state.isBookAvailable) {
                                Column {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        AsyncImage(
                                            model = viewModel.state.book.cover,
                                            contentDescription = "book cover",
                                            modifier = Modifier.requiredSize(100.dp, 150.dp),
                                            placeholder = painterResource(id = R.drawable.image_48px),
                                            error = painterResource(id = R.drawable.image_48px)
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.title != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.title_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Title",
                                            text = viewModel.state.title
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.author != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.person_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Author",
                                            text = viewModel.state.author
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.publisher != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.apartment_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Publisher",
                                            text = viewModel.state.publisher
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.publish_places != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.location_on_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Published Places",
                                            text = viewModel.state.publish_places
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.publish_date != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.event_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Published Date",
                                            text = viewModel.state.publish_date
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.page_count != "0") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.numbers_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Page Count",
                                            text = viewModel.state.page_count
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.weight != "") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.scale_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Weight",
                                            text = viewModel.state.weight
                                        )
                                    }
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Add manually", modifier = Modifier.padding(end = 12.dp))
                            Switch(
                                checked = viewModel.state.manual,
                                onCheckedChange = { viewModel.onEvent(AddBookEvent.ToggleState) })
                        }

                    }

                    item {
                        AnimatedVisibility(viewModel.state.manual) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = ShapeDefaults.ExtraLarge,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                            ) {
                                Column(Modifier.padding(12.dp)) {

                                    OutlinedTextField(
                                        value = viewModel.state.title,
                                        onValueChange = {
                                            viewModel.state = viewModel.state.copy(title = it)
                                        },
                                        label = { Text(text = "Title") },
                                        singleLine = true,
                                        shape = ShapeDefaults.Large,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.title_24px),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                    OutlinedTextField(
                                        value = viewModel.state.author,
                                        onValueChange = {
                                            viewModel.state = viewModel.state.copy(author = it)
                                        },
                                        label = { Text(text = "Author") },
                                        singleLine = true,
                                        shape = ShapeDefaults.Large,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.person_24px),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                    OutlinedTextField(
                                        value = viewModel.state.publisher,
                                        onValueChange = {
                                            viewModel.state = viewModel.state.copy(publisher = it)
                                        },
                                        label = { Text(text = "Publisher") },
                                        singleLine = true,
                                        shape = ShapeDefaults.Large,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.apartment_24px),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                    OutlinedTextField(
                                        value = viewModel.state.publish_date,
                                        onValueChange = {
                                            viewModel.state =
                                                viewModel.state.copy(publish_date = it)
                                        },
                                        label = { Text(text = "Publish date") },
                                        singleLine = true,
                                        shape = ShapeDefaults.Large,
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Next
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.event_24px),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                    OutlinedTextField(
                                        value = viewModel.state.page_count,
                                        onValueChange = {
                                            viewModel.state = viewModel.state.copy(page_count = it)
                                        },
                                        shape = ShapeDefaults.Large,
                                        label = { Text(text = "Page count") },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        ),
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth(),
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.numbers_24px),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
