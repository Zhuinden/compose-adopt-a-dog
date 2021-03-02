package com.zhuinden.androiddevchallenge.features.dogdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.zhuinden.androiddevchallenge.core.navigation.ComposeKey
import com.zhuinden.androiddevchallenge.data.models.Dog
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class DogDetailKey(val dog: Dog): ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        DogDetailScreen(dog = dog)
    }
}