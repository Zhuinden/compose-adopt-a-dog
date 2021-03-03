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
package com.zhuinden.androiddevchallenge.features.dogdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.zhuinden.androiddevchallenge.core.models.contentDescription
import com.zhuinden.androiddevchallenge.data.models.Dog
import dev.chrisbanes.accompanist.coil.CoilImage
import okhttp3.HttpUrl
import java.util.Locale

@Composable
fun DogDetailScreen(dog: Dog) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(state = scrollState)) {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp, max = 280.dp),
            request = ImageRequest.Builder(context).data(HttpUrl.parse(dog.imageUrl)).build(),
            contentDescription = dog.contentDescription(),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth,
            loading = { CircularProgressIndicator() },
        )

        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Text(
                text = dog.name,
                style = MaterialTheme.typography.h2,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${dog.age}, ${dog.determinedSex.name.toLowerCase(Locale.getDefault())}",
                style = MaterialTheme.typography.h3,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${dog.name} is a cute ${dog.breed} and is waiting to be adopted, taken home, handled with care and treated with love. You should definitely do your best to adopt ${dog.name}!",
                style = MaterialTheme.typography.body1,
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
