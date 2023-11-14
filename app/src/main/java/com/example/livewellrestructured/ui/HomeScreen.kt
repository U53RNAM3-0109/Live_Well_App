package com.example.livewellrestructured.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livewellrestructured.data.openSans
import com.example.livewellrestructured.data.parseHtml
import com.example.livewellrestructured.network.LiveWellAppData

@Composable
fun HomeScreen(
    liveWellUiState: LiveWellUiState,
    uriHandler: UriHandler,
    onNavigationToWebsite: () -> Unit,
    onNavigationToContactUs: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("Helping support the Community.", fontFamily = openSans)
        }
        //Contact us page button
        item {
            Button(onClick = onNavigationToContactUs) {
                Text("Contact Us", fontFamily = openSans)
            }
        }
        //Navigate to website button (opens in new tab)
        item {
            Button(onClick = onNavigationToWebsite) {
                Text("Visit our Website for more information", fontFamily = openSans)
            }
        }
        //Only loads the next section when the data has been retrieved
        when (liveWellUiState) {
            is LiveWellUiState.Loading -> LoadingScreen(lazyListScope = this, modifier = modifier.fillMaxSize())
            is LiveWellUiState.Success -> SuccessScreen(
                lazyListScope = this,
                liveWellUiState.appData,
                uriHandler,
                modifier = modifier.fillMaxSize()
            )

            is LiveWellUiState.Error -> ErrorScreen(lazyListScope = this)
        }

    }

}

fun LoadingScreen(lazyListScope :LazyListScope, modifier: Modifier = Modifier) {
    lazyListScope.item {
        Text("Loading content...", fontFamily = openSans)
    }
}

fun SuccessScreen(
    lazyListScope :LazyListScope,
    appData: LiveWellAppData,
    uriHandler: UriHandler,
    modifier: Modifier = Modifier
) {
        lazyListScope.item {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                for (section in appData.sections) {
                    Text(
                        section.getValue("sectionTitle")[0].getValue("title").parseHtml(),
                        fontSize = 22.sp,
                        fontFamily = openSans,
                        fontWeight = FontWeight.Bold
                    )
                    val content: List<Map<String, String>> = (section["content"]
                        ?: emptyList<Map<String, String>>()) as List<Map<String, String>>

                    for (contentItem: Map<String, String> in content) {
                        Column(modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text(
                                contentItem.getValue("header").parseHtml(),
                                fontFamily = openSans,
                                fontWeight = FontWeight.Bold
                            )
                            Text(contentItem.getValue("paragraph").parseHtml(), fontFamily = openSans)
                            if (contentItem["uri"] != "") {
                                Button(onClick = { uriHandler.openUri(contentItem.getValue("uri")) }) {
                                    Text(
                                        contentItem.getValue("buttonText").parseHtml(),
                                        fontFamily = openSans,
                                        fontWeight = FontWeight.SemiBold,
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            }
                        }
                        Spacer(modifier.height(8.dp))
                    }
                }



        }
    }
}


fun ErrorScreen(lazyListScope :LazyListScope, modifier: Modifier = Modifier) {
    lazyListScope.item {
        Text("Error: Cannot connect to the server. Try again later.", fontFamily = openSans)
    }
}