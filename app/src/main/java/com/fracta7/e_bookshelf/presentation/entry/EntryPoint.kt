package com.fracta7.e_bookshelf.presentation.entry

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Genres
import com.fracta7.e_bookshelf.presentation.composable_elements.LibraryCategory
import com.fracta7.e_bookshelf.presentation.composable_elements.sampleBooks
import com.fracta7.e_bookshelf.presentation.destinations.AddBookScreenDestination
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
fun AnimatedVisibilityScope.EntryPoint(
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
        BackHandler(onBack = {
            scope.launch {
                viewModel.state.drawerState.close()
            }
        },
        enabled = viewModel.state.drawerState.isOpen)
    }
    var selected by remember { mutableStateOf(false) }
    EbookshelfTheme(darkTheme = viewModel.state.darkTheme, dynamicColor = false) {
        Surface(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
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
                            Genres.list.forEach { item ->
                                item {
                                    NavigationDrawerItem(
                                        label = {
                                            Text(
                                                text = item,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        selected = selected,
                                        onClick = { /*TODO*/ })
                                }
                            }
                        }

                    }
                },
                drawerState = viewModel.state.drawerState
            ) {
                Scaffold(
                    topBar = {
                        LargeTopAppBar(
                            title = {
                                Text(
                                    text = "Library",
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
                            scrollBehavior = scrollBehavior
                        )
                    },
                    content = {

                        LazyColumn(
                            contentPadding = it,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(Genres.list.size) { it1 ->
                                LibraryCategory(
                                    title = Genres.list[it1],
                                    books = sampleBooks,
                                    modifier = Modifier.padding(vertical = 12.dp)
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { navigator.navigate(AddBookScreenDestination(darkTheme = viewModel.state.darkTheme)) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_24px),
                                contentDescription = "add new book"
                            )
                        }
                    }
                )
            }
        }
    }
}