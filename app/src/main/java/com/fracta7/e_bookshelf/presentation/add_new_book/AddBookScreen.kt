package com.fracta7.e_bookshelf.presentation.add_new_book

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Genres
import com.fracta7.e_bookshelf.presentation.composable_elements.InfoSection
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun AnimatedVisibilityScope.AddBookScreen(
    navigator: DestinationsNavigator,
    darkTheme: Boolean = true
) {
    val viewModel = hiltViewModel<AddBookViewModel>()
    var isbn by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var publisher by remember { mutableStateOf("") }
    var publish_date by remember { mutableStateOf("") }
    var number_of_pages by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = viewModel.state.book.cover)
            .apply(block = fun ImageRequest.Builder.() {
                placeholder(R.drawable.book_cover)
                error(R.drawable.book_cover)
            }).build()
    )

    EbookshelfTheme(darkTheme = darkTheme) {
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
                    FloatingActionButton(onClick = { viewModel.onEvent(AddBookEvent.AddBook) }) {
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
                                value = isbn,
                                onValueChange = {
                                    isbn = it
                                    viewModel.onEvent(AddBookEvent.CheckBook(isbn))
                                },
                                shape = ShapeDefaults.Large,
                                label = { Text(text = "ISBN") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                                    .fillMaxWidth()
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
                            var textFiledSize by remember { mutableStateOf(Size.Zero) }
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

                            OutlinedTextField(
                                value = selectedItem,
                                onValueChange = { selectedItem = it },
                                modifier = Modifier
                                    .clickable {
                                        expanded = !expanded
                                    }
                                    .onGloballyPositioned { coordinates ->
                                        textFiledSize = coordinates.size.toSize()
                                    }
                                    .padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                                    .fillMaxWidth(),
                                label = { Text(text = "Select the genre") },
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
                                    disabledBorderColor = selectColor,
                                    disabledLabelColor = selectColor,
                                    disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = ShapeDefaults.Large
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.93f)
                            ) {
                                LazyColumn(
                                    modifier = Modifier.size(
                                        width = with(LocalDensity.current) { textFiledSize.width.toDp() },
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

                            //animate info section depending on the available info individually
                            AnimatedVisibility(viewModel.state.isBookAvailable) {
                                Column{
                                    AnimatedVisibility(viewModel.state.author!="") {
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.person_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Author",
                                            text = viewModel.state.author
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.publisher!=""){
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.apartment_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Publisher",
                                            text = viewModel.state.publisher
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.publish_places!=""){
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.location_on_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Published Places",
                                            text = viewModel.state.publish_places
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.publish_date!=""){
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.event_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Published Date",
                                            text = viewModel.state.publish_date
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.page_count!="0"||viewModel.state.page_count!=""){
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.numbers_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Page Count",
                                            text = viewModel.state.page_count
                                        )
                                    }
                                    AnimatedVisibility(viewModel.state.weight!=""){
                                        InfoSection(
                                            painter = painterResource(id = R.drawable.scale_24px),
                                            modifier = Modifier.padding(start = 16.dp, 4.dp),
                                            label = "Weight",
                                            text = viewModel.state.weight
                                        )
                                    }
                                }
                            }
/*
                            //info section
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                            ) {
                                val color = MaterialTheme.colorScheme.secondaryContainer
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.person_24px),
                                            contentDescription = "author icon",
                                            modifier = Modifier
                                                .drawBehind {
                                                    drawCircle(color = color, radius = size.width/1.5f)
                                                },
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                        Column(modifier = Modifier.padding(6.dp)) {
                                            Text(
                                                text = "Author",
                                                style = MaterialTheme.typography.labelMedium
                                            )
                                            Text(
                                                text = viewModel.state.author,
                                                style = MaterialTheme.typography.labelSmall,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.event_24px),
                                            contentDescription = "author icon",
                                            modifier = Modifier
                                                .drawBehind {
                                                    drawCircle(color = color, radius = size.width/1.5f)
                                                },
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                        Column(modifier = Modifier.padding(6.dp)) {
                                            Text(
                                                text = "Published Date",
                                                style = MaterialTheme.typography.labelMedium
                                            )
                                            Text(
                                                text = viewModel.state.publish_date,
                                                style = MaterialTheme.typography.labelSmall,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.numbers_24px),
                                            contentDescription = "author icon",
                                            modifier = Modifier
                                                .drawBehind {
                                                    drawCircle(color = color, radius = size.width/1.5f)
                                                },
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                        Column(modifier = Modifier.padding(6.dp)) {
                                            Text(
                                                text = "Page Count",
                                                style = MaterialTheme.typography.labelMedium
                                            )
                                            Text(
                                                text = viewModel.state.page_count,
                                                style = MaterialTheme.typography.labelSmall,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.location_on_24px),
                                            contentDescription = "author icon",
                                            modifier = Modifier
                                                .drawBehind {
                                                    drawCircle(color = color, radius = size.width/1.5f)
                                                },
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                        Column(modifier = Modifier.padding(6.dp)) {
                                            Text(
                                                text = "Published Places",
                                                style = MaterialTheme.typography.labelMedium
                                            )
                                            Text(
                                                text = viewModel.state.publish_places,
                                                style = MaterialTheme.typography.labelSmall,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }
                            */
//end of info panel
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
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Image(
                                            painter = painter,
                                            contentDescription = "book cover",
                                            modifier = Modifier.requiredSize(100.dp, 150.dp)
                                        )
                                    }

                                    OutlinedTextField(
                                        value = title,
                                        onValueChange = { title = it },
                                        label = { Text(text = "Title") },
                                        singleLine = true,
                                        shape = ShapeDefaults.Large,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next
                                        ),
                                        enabled = !viewModel.state.isBookAvailable,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = author,
                                        onValueChange = { author = it },
                                        label = { Text(text = "Author") },
                                        singleLine = true,
                                        shape = ShapeDefaults.Large,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next
                                        ),
                                        enabled = !viewModel.state.isBookAvailable,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = publisher,
                                        onValueChange = { publisher = it },
                                        label = { Text(text = "Publisher") },
                                        singleLine = true,
                                        shape = ShapeDefaults.Large,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next
                                        ),
                                        enabled = !viewModel.state.isBookAvailable,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = publish_date,
                                        onValueChange = { publish_date = it },
                                        label = { Text(text = "Publish date") },
                                        singleLine = true,
                                        shape = ShapeDefaults.Large,
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Next
                                        ),
                                        enabled = !viewModel.state.isBookAvailable,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = number_of_pages,
                                        onValueChange = { number_of_pages = it },
                                        shape = ShapeDefaults.Large,
                                        label = { Text(text = "Page count") },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        ),
                                        singleLine = true,
                                        enabled = !viewModel.state.isBookAvailable,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = description,
                                        onValueChange = { description = it },
                                        label = { Text("Description (optional)") },
                                        modifier = Modifier
                                            .height(100.dp)
                                            .fillMaxWidth(),
                                        shape = ShapeDefaults.Large,
                                        enabled = !viewModel.state.isBookAvailable
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
