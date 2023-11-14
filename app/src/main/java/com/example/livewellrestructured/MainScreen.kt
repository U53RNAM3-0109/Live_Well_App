package com.example.livewellrestructured

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.livewellrestructured.data.openSans
import com.example.livewellrestructured.ui.ContactUsScreen
import com.example.livewellrestructured.ui.HomeScreen
import com.example.livewellrestructured.ui.LiveWellViewModel

enum class LiveWellScreen(@StringRes val title: Int, val canNavigateBack: Boolean) {
    Home(title=R.string.home_title, canNavigateBack = false),
    YouthSupport(title=R.string.youth_support, canNavigateBack = true),
    ContactUs(title=R.string.contact_us, canNavigateBack = true);
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveWellBar(
    currentScreen: LiveWellScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(title = { Text(stringResource(currentScreen.title), fontFamily = openSans) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        })
}


@Composable
fun LiveWellApp(
    liveWellViewModel: LiveWellViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = LiveWellScreen.valueOf(
        backStackEntry?.destination?.route ?: LiveWellScreen.Home.name
    )

    val liveWellUiState = liveWellViewModel.liveWellUiState

    Scaffold(topBar = {
        LiveWellBar(currentScreen=currentScreen,
            canNavigateBack = currentScreen.canNavigateBack,
            navigateUp = {navController.navigateUp() })
    })
    { innerPadding ->
        val uriHandler = LocalUriHandler.current
        NavHost(
            navController = navController,
            startDestination = LiveWellScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LiveWellScreen.Home.name) {
                HomeScreen(
                    liveWellUiState,
                    uriHandler,
                    onNavigationToYouth = {
                        navController.navigate(LiveWellScreen.YouthSupport.name)
                    },
                    onNavigationToContactUs = {
                        navController.navigate(LiveWellScreen.ContactUs.name)
                    },
                    onNavigationToWebsite = {
                        uriHandler.openUri("https://www.livewellinbraunton.co.uk")
                    })
            }
            /*composable(route = LiveWellScreen.YouthSupport.name) {
                YouthSupportScreen(liveWellUiState, uriHandler)
            }*/
            composable(route = LiveWellScreen.ContactUs.name) {
                ContactUsScreen()
            }
        }
    }
}