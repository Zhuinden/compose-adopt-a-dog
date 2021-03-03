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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.zhuinden.androiddevchallenge.core.navigation.ComposeKey
import com.zhuinden.androiddevchallenge.data.models.Dog
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class DogDetailKey(val dog: Dog) : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        DogDetailScreen(dog = dog)
    }
}
