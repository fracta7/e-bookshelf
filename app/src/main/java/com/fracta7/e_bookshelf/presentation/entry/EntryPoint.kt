package com.fracta7.e_bookshelf.presentation.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.presentation.composable_elements.LibraryCategory
import com.fracta7.e_bookshelf.presentation.composable_elements.sampleBooks
import com.fracta7.e_bookshelf.presentation.composable_elements.sampleCategories
import com.fracta7.e_bookshelf.presentation.destinations.AddBookScreenDestination
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPoint(
    navigator: DestinationsNavigator
) {
    rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<EntryPointViewModel>()
    var selected by remember { mutableStateOf(false) }
    EbookshelfTheme(darkTheme = viewModel.state.darkTheme, dynamicColor = false) {
        Surface(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                                    painter = painterResource(id = R.drawable.ic_baseline_invert_colors_24),
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

                        sampleCategories.forEach { item ->
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
                                        painter = painterResource(id = R.drawable.ic_baseline_dehaze_24),
                                        contentDescription = "",
                                        modifier = Modifier.requiredSize(32.dp)
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior,
                            actions = {
                                IconButton(
                                    onClick = {
                                        navigator.navigate(AddBookScreenDestination(viewModel.state.darkTheme))
                                    }

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                                        contentDescription = "",
                                        modifier = Modifier.requiredSize(32.dp)
                                    )
                                }
                            }
                        )
                    },
                    content = {
                        LazyColumn(
                            contentPadding = it,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(sampleCategories.size) { it1 ->
                                LibraryCategory(
                                    title = sampleCategories[it1],
                                    books = sampleBooks,
                                    modifier = Modifier.padding(vertical = 12.dp)
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}