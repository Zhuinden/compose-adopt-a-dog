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