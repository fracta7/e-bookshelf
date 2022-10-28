package com.fracta7.e_bookshelf.presentation.reading_list.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.presentation.destinations.EntryPointDestination
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ReadingListAddScreen(navigator: DestinationsNavigator) {
    val viewModel = hiltViewModel<ReadingListAddScreenViewModel>()
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
                            Text(text = "Add new Reading List")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navigator.navigateUp()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_back_24px),
                                    contentDescription = "Back"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        viewModel.onEvent(ReadingListAddScreenEvents.AddReadingList(viewModel.state.readingListName))
                        if (!viewModel.state.alreadyExists) navigator.navigate(EntryPointDestination)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_24px),
                            contentDescription = "Add"
                        )
                    }
                }
            ) { it ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(it)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            OutlinedTextField(
                                value = viewModel.state.readingListName,
                                onValueChange = {
                                    viewModel.state = viewModel.state.copy(readingListName = it)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            val status =
                                if (viewModel.state.alreadyExists) "Reading list already exist" else ""
                            Text(text = status, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}