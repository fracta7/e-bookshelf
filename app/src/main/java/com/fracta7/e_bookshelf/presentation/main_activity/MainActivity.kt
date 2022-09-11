package com.fracta7.e_bookshelf.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.presentation.composable_elements.sampleBooks
import com.fracta7.e_bookshelf.presentation.composable_elements.sampleCategories
import com.fracta7.e_bookshelf.presentation.main_screen.MainScreen
import com.fracta7.e_bookshelf.presentation.ui.theme.EbookshelfTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            val scope = rememberCoroutineScope()
            val viewModel = hiltViewModel<MainActivityViewModel>()
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
                                                viewModel.onEvent(MainActivityEvent.ChangeTheme)
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

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp)
                                            .clickable { /*not done*/ },
                                        shape = ShapeDefaults.Large,
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                    ) {
                                        Text(
                                            text = item,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(20.dp)
                                        )
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
                                        IconButton(onClick = {
                                            scope.launch {
                                                viewModel.openDrawer()
                                            }

                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_baseline_dehaze_24),
                                                contentDescription = ""
                                            )
                                        }
                                    },
                                    scrollBehavior = scrollBehavior
                                )
                            },
                            content = {
                                MainScreen(
                                    contentPadding = it,
                                    categories = sampleCategories,
                                    books = sampleBooks
                                )

                            }
                        )
                    }
                }
            }
        }
    }
}