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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import com.zhuinden.androiddevchallenge.core.navigation.ComposeKey
import com.zhuinden.androiddevchallenge.data.datasource.DogDataSource
import com.zhuinden.androiddevchallenge.utils.OptionalWrapper
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackcomposeintegration.services.rememberService
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
class DogListKey : ComposeKey() {
    @Suppress("RemoveExplicitTypeArguments")
    override fun bindServices(serviceBinder: ServiceBinder) {
        super.bindServices(serviceBinder)
        with(serviceBinder) {
            add(DogListViewModel(lookup<DogDataSource>()))
        }
    }

    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        val viewModel = rememberService<DogListViewModel>()

        val dogs = viewModel.dogList.subscribeAsState(OptionalWrapper.absent())
        val lazyListState = viewModel.lazyListState

        DogListScreen(dogs.value.value, lazyListState)
    }
}
