package com.schoolproject.droidshop.presentation.home_screen

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.schoolproject.droidshop.presentation.components.SectionText
import com.schoolproject.droidshop.presentation.components.TabCategory
import com.schoolproject.droidshop.presentation.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
 modifier: Modifier = Modifier
){

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val scrollable = rememberScrollState()



            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        windowInsets = WindowInsets(0, 0, 0, 0),

                        title = {

                        },
                        navigationIcon = {
                            Text("Droid Shop")
                        },
                        actions = {

                        }
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .navigationBarsPadding()
                            .verticalScroll(rememberScrollState())

                    ) {

                        ImageSliderSection()

                        Spacer(modifier = Modifier.height(16.dp))

                        CategorySection(
                            title = "Category",
                            content = {
                                TabCategory(
                                    gridHeight = 640.dp,
                                    limit = 4,
                                    navigateToDetail = { productId ->

                                    },
                                    snackbarHostState = snackbarHostState,
                                    scope = scope,
                                    modifier = Modifier.padding(8.dp)
                                )
                            },
                            navigateToSeeAll = {}
                        )


                    }
                }
            )





}

@Composable
fun CategorySection(
    title: String,
    title2: String = "See all",
    navigateToSeeAll: () -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        if (title != ""){
           SectionText(
               "Categories",
               text2 = title2

           )
        }
        content()
    }

}