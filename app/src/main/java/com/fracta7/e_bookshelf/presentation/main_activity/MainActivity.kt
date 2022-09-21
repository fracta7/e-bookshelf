package com.fracta7.e_bookshelf.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Alignment
import com.fracta7.e_bookshelf.presentation.NavGraphs
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val speed = 300
            val navHostEngine = rememberAnimatedNavHostEngine(
                navHostContentAlignment = Alignment.TopCenter,
                rootDefaultAnimations = RootNavGraphDefaultAnimations(
                    enterTransition = {
                        slideInHorizontally(
                            animationSpec = tween(speed),
                            initialOffsetX = { 1000 })
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            animationSpec = tween(speed),
                            targetOffsetX = { 1000 })
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            animationSpec = tween(speed),
                            initialOffsetX = { 1000 })
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            animationSpec = tween(speed),
                            targetOffsetX = { 1000 })
                    }
                ), //default `rootDefaultAnimations` means no animations
                defaultAnimationsForNestedNavGraph = mapOf(
                    NavGraphs.root to NestedNavGraphDefaultAnimations(
                        enterTransition = {
                            slideInHorizontally(
                                animationSpec = tween(speed),
                                initialOffsetX = { 1000 })
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                animationSpec = tween(speed),
                                targetOffsetX = { 1000 })
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                animationSpec = tween(speed),
                                initialOffsetX = { 1000 })
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                animationSpec = tween(speed),
                                targetOffsetX = { 1000 })
                        }
                    ),
                    NavGraphs.root to NestedNavGraphDefaultAnimations.ACCOMPANIST_FADING
                ) // all other nav graphs not specified in this map, will get their animations from the `rootDefaultAnimations` above.
            )
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                engine = navHostEngine,
                navController = rememberAnimatedNavController()
            )
        }
    }
}