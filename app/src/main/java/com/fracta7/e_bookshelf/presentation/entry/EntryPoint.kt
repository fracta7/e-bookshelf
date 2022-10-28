package com.fracta7.e_bookshelf.presentation.entry

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.domain.model.Genres
import com.fracta7.e_bookshelf.domain.model.ReadingList
import com.fracta7.e_bookshelf.presentation.composable_elements.ExpandedBookCard
import com.fracta7.e_bookshelf.presentation.composable_elements.LibraryCategory
import com.fracta7.e_bookshelf.presentation.destinations.AddBookScreenDestination
import com.fracta7.e_bookshelf.presentation.destinations.CategoryViewDestination
import com.fracta7.e_bookshelf.presentation.destinations.ReadingListAddScreenDestination
import com.fracta7.e_bookshelf.presentation.destinations.ReadingListViewDestination
import com.fracta7.e_bookshelf.presentation.reading_list.ReadingListAll
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination(style = EntryPointAnimations::class)
@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun EntryPoint(
    navigator: DestinationsNavigator
) {
    var showDialog by remember { mutableStateOf(false) }
    val activity = LocalContext.current as? Activity
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<EntryPointViewModel>()
    if (viewModel.state.drawerState.isClosed) {
        BackHandler(
            onBack = {
                showDialog = !showDialog
            },
            enabled = viewModel.state.drawerState.isClosed
        )
    } else {
        BackHandler(
            onBack = {
                scope.launch {
                    viewModel.state.drawerState.close()
                }
            },
            enabled = viewModel.state.drawerState.isOpen
        )
    }
    val items = listOf(0, 1, 2, 3)
    val navItems = listOf("Home", "Categories", "Reading Lists", "Settings")
    val navIcons = listOf(
        R.drawable.home_24px,
        R.drawable.category_24px,
        R.drawable.fact_check_24px,
        R.drawable.tune_24px
    )
    val selectedItem = remember { mutableStateOf(items[0]) }
    var title by remember { mutableStateOf("Home") }
    EbookshelfTheme(
        darkTheme = viewModel.state.darkTheme,
        dynamicColor = viewModel.state.dynamicTheme
    ) {
        Surface() {
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            activity?.finish()
                        }) {
                            Text(text = "Yes")
                        }
                    },
                    title = {
                        Text(text = "Quit")
                    },
                    text = {
                        Text(text = "Are you sure you want to quit?")
                    }
                )
            }

            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val themeIcon = if (!viewModel.state.darkTheme) {
                                painterResource(id = R.drawable.dark_mode_24px)
                            } else {
                                painterResource(id = R.drawable.light_mode_24px)
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_invert_colors_24),
                                contentDescription = "",
                                modifier = Modifier.padding(20.dp)
                            )
                            FilledTonalButton(
                                onClick = {
                                    viewModel.onEvent(EntryPointEvent.ChangeTheme)
                                },
                                modifier = Modifier.padding(20.dp)
                            ) {
                                Icon(
                                    painter = themeIcon,
                                    contentDescription = "Change theme"
                                )
                            }
                        }

                        Text(
                            text = "E-Bookshelf",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(20.dp)
                        )
                        Divider(modifier = Modifier.fillMaxWidth())

                        LazyColumn() {
                            items(navItems.size) {
                                NavigationDrawerItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = navIcons[it]),
                                            contentDescription = navItems[it]
                                        )
                                    },
                                    label = { Text(navItems[it]) },
                                    selected = selectedItem.value == it,
                                    onClick = {
                                        selectedItem.value = it
                                        title = navItems[it]
                                        scope.launch { viewModel.closeDrawer() }
                                    })
                            }
                        }

                    }
                },
                drawerState = viewModel.state.drawerState
            ) {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        LargeTopAppBar(
                            title = {
                                Text(
                                    text = title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            viewModel.openDrawer()
                                        }
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.menu_24px),
                                        contentDescription = "",
                                        modifier = Modifier.requiredSize(32.dp)
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior,
                            actions = {
                                TextButton(onClick = {
                                    navigator.navigate(ReadingListAddScreenDestination())
                                }) {
                                    Text(text = "Add New Reading List")
                                }
                            }
                        )
                    },
                    content = { it2 ->
                        AnimatedVisibility(selectedItem.value == 1) {
                            AllGenresScreen(
                                navigator = navigator,
                                isDbEmpty = viewModel.state.isDbEmpty,
                                isLoading = viewModel.state.isLoading,
                                contentPadding = it2,
                                books = viewModel.state.books
                            )
                        }
                        AnimatedVisibility(selectedItem.value == 0) {
                            HomeScreen(
                                navigator = navigator,
                                isDbEmpty = viewModel.state.isDbEmpty,
                                isLoading = viewModel.state.isLoading,
                                contentPadding = it2,
                                books = viewModel.state.books
                            )
                        }
                        AnimatedVisibility(visible = selectedItem.value == 2) {
                            ReadingListScreen(
                                navigator = navigator,
                                isDbEmpty = viewModel.state.isDbEmpty,
                                isLoading = viewModel.state.isLoading,
                                contentPadding = it2,
                                readingList = viewModel.state.readingList,
                                books = viewModel.state.books
                            )
                        }
                        AnimatedVisibility(selectedItem.value == 3) {
                            SettingsScreen(
                                paddingValues = it2,
                                isDbEmpty = viewModel.state.isDbEmpty,
                                darkTheme = viewModel.state.darkTheme,
                                dynamicTheme = viewModel.state.dynamicTheme,
                                switchDynamic = {
                                    viewModel.onEvent(EntryPointEvent.ToggleDynamicTheme)
                                },
                                switchDark = {
                                    viewModel.onEvent(EntryPointEvent.ChangeTheme)
                                },
                                deleteDb = {
                                    viewModel.onEvent(EntryPointEvent.DeleteDb)
                                })
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { navigator.navigate(AddBookScreenDestination()) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_24px),
                                contentDescription = "add a new book"
                            )
                        }
                    }
                )
            }
        }

    }
}

@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    isDbEmpty: Boolean,
    isLoading: Boolean,
    contentPadding: PaddingValues,
    books: List<Book>
) {
    AnimatedVisibility(isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
    AnimatedVisibility(!isDbEmpty) {
        LazyColumn(
            contentPadding = contentPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(books.size) {
                val currentBook = books[it]
                ExpandedBookCard(navigator = navigator, book = currentBook)
            }
        }
    }
    AnimatedVisibility(isDbEmpty && !isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Your library is empty", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.layers_clear_24px),
                contentDescription = null,
                modifier = Modifier.requiredSize(64.dp)
            )
        }
    }
}

@Composable
fun AllGenresScreen(
    navigator: DestinationsNavigator,
    isDbEmpty: Boolean,
    isLoading: Boolean,
    contentPadding: PaddingValues,
    books: List<Book>
) {
    AnimatedVisibility(isLoading) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            CircularProgressIndicator()
        }
    }
    AnimatedVisibility(!isDbEmpty) {
        LazyColumn(
            contentPadding = contentPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(Genres.list.size) { it1 ->
                val currentGenre = Genres.list[it1]
                val currentItem =
                    books.filter { it.genre == Genres.list[it1] }
                AnimatedVisibility(currentItem.isNotEmpty()) {
                    LibraryCategory(
                        title = currentGenre,
                        books = currentItem,
                        modifier = Modifier.padding(vertical = 12.dp),
                        navigator = navigator,
                        onClick = {
                            navigator.navigate(
                                CategoryViewDestination(
                                    category = currentGenre
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    AnimatedVisibility(isDbEmpty && !isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Your library is empty", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.layers_clear_24px),
                contentDescription = null,
                modifier = Modifier.requiredSize(64.dp)
            )
        }
    }

}

@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    isDbEmpty: Boolean,
    darkTheme: Boolean,
    dynamicTheme: Boolean,
    switchDynamic: () -> Unit,
    switchDark: () -> Unit,
    deleteDb: () -> Unit
) {
    var dynamic by remember { mutableStateOf(dynamicTheme) }
    var dark by remember { mutableStateOf(darkTheme) }
    var empty by remember { mutableStateOf(isDbEmpty) }
    val isAndroidS = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    var showDelete by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues
    ) {
        item {
            Card(modifier = Modifier.fillMaxWidth(), shape = ShapeDefaults.ExtraLarge) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            if (isAndroidS) {
                                switchDynamic()
                                dynamic = !dynamic
                            }
                        }
                        .padding(12.dp)
                ) {

                    Text(
                        text = "Enable Dynamic Colors (Android 12+)",
                        color = if (isAndroidS) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Switch(
                        checked = dynamic,
                        onCheckedChange = {
                            switchDynamic()
                            dynamic = !dynamic
                        },
                        enabled = isAndroidS
                    )
                }

                Divider(Modifier.fillMaxWidth())

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            dark = !dark
                            switchDark()
                        }
                        .padding(12.dp)
                ) {

                    Text(
                        text = "Enable Dark Theme"
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Switch(
                        checked = dark,
                        onCheckedChange = {
                            switchDark()
                            dark = !dark
                        }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.padding(12.dp))
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (!empty) {
                            showDelete = !showDelete
                        }
                    },
                shape = ShapeDefaults.ExtraLarge
            ) {
                Text(
                    text = "Delete all books",
                    modifier = Modifier.padding(12.dp),
                    color = if (empty) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error
                )
            }
            AnimatedVisibility(showDelete) {
                AlertDialog(
                    onDismissRequest = {
                        showDelete = false
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDelete = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDelete = false
                            deleteDb()
                            empty = true
                        }) {
                            Text(text = "Yes")
                        }
                    },
                    title = {
                        Text(text = "Delete ALL BOOKS?")
                    },
                    text = {
                        Text(text = "Are you sure you want to DELETE ALL BOOKS?")
                    }
                )
            }

        }
    }
}

@Composable
fun ReadingListScreen(
    navigator: DestinationsNavigator,
    isDbEmpty: Boolean,
    isLoading: Boolean,
    contentPadding: PaddingValues,
    readingList: List<ReadingList>,
    books: List<Book>
) {
    AnimatedVisibility(isLoading) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            CircularProgressIndicator()
        }
    }
    AnimatedVisibility(!isDbEmpty) {
        LazyColumn(
            contentPadding = contentPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                val filteredBooks = books.filter { it.readingList == "Default" }
                AnimatedVisibility(visible = filteredBooks.isNotEmpty()) {
                    ReadingListAll(title = "Default", bookCount = filteredBooks.size) {
                        navigator.navigate(ReadingListViewDestination(readingList = "Default"))
                    }
                }
            }
            items(readingList.size) { it1 ->
                val currentReadingList = readingList[it1].name
                val currentItem =
                    books.filter { it.readingList == readingList[it1].name }
                AnimatedVisibility(currentItem.isNotEmpty()) {
                    if (currentReadingList != null) {
                        ReadingListAll(
                            title = readingList[it1].name.toString(),
                            bookCount = currentItem.size
                        ) {
                            navigator.navigate(ReadingListViewDestination(readingList = readingList[it1].name.toString()))
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(isDbEmpty && !isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Your library is empty", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.layers_clear_24px),
                contentDescription = null,
                modifier = Modifier.requiredSize(64.dp)
            )
        }
    }
}