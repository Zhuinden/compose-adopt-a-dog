/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhuinden.androiddevchallenge.features.doglist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import coil.request.ImageRequest
import coil.size.PixelSize
import com.zhuinden.androiddevchallenge.core.models.contentDescription
import com.zhuinden.androiddevchallenge.data.models.Dog
import com.zhuinden.androiddevchallenge.features.dogdetail.DogDetailKey
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack
import dev.chrisbanes.accompanist.coil.CoilImage
import okhttp3.HttpUrl

@Composable
fun DogListScreen(dogs: List<Dog>?, lazyListState: LazyListState) {
    @Composable
    fun DogItem(dog: Dog) {
        val context = LocalContext.current
        val backstack = LocalBackstack.current

        var fullWidth by remember { mutableStateOf(0) }

        val density = LocalDensity.current

        Layout(
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (fullWidth == 0) {
                        CircularProgressIndicator()
                    } else {
                        CoilImage(
                            request = ImageRequest.Builder(context)
                                .size(density.run { PixelSize(fullWidth, 160.dp.toPx().toInt()) })
                                .data(HttpUrl.parse(dog.imageUrl))
                                .build(),
                            contentDescription = dog.contentDescription(),
                            fadeIn = true,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.clickable {
                                backstack.goTo(DogDetailKey(dog))
                            },
                            loading = {
                                CircularProgressIndicator()
                            },
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                val placeables = measurables.fastMap { it.measure(constraints) }
                val maxWidth = placeables.fastMaxBy { it.width }?.width ?: 0
                val maxHeight = placeables.fastMaxBy { it.height }?.height ?: 0

                if (fullWidth == 0) {
                    fullWidth = maxWidth
                }

                layout(maxWidth, maxHeight) {
                    placeables.fastForEach { placeable ->
                        placeable.place(0, 0)
                    }
                }
            }
        )
    }

    @Composable
    fun DogList(dogs: List<Dog>, lazyListState: LazyListState) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), state = lazyListState,
                content = {
                    this.items(
                        dogs.size,
                        itemContent = { index ->
                            DogItem(dogs[index])
                        }
                    )
                }
            )
        }
    }

    @Composable
    fun LoadingIndicator() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun Header() {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Row {
                    Text("Adopt A Dog")
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Header()

        if (dogs == null) {
            LoadingIndicator()
        } else {
            DogList(dogs, lazyListState)
        }
    }
}
