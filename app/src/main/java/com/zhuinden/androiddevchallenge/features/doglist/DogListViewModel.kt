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

import androidx.compose.foundation.lazy.LazyListState
import com.jakewharton.rxrelay2.BehaviorRelay
import com.zhuinden.androiddevchallenge.data.datasource.DogDataSource
import com.zhuinden.androiddevchallenge.data.models.Dog
import com.zhuinden.androiddevchallenge.utils.OptionalWrapper
import com.zhuinden.simplestack.Bundleable
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.statebundle.StateBundle
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class DogListViewModel(
    private val dogDataSource: DogDataSource
) : ScopedServices.Registered, Bundleable {
    private val dogListRelay =
        BehaviorRelay.createDefault<OptionalWrapper<List<Dog>>>(OptionalWrapper.absent())
    val dogList: Observable<OptionalWrapper<List<Dog>>> = dogListRelay

    var lazyListState: LazyListState = LazyListState(0, 0)
        private set

    private val compositeDisposable = CompositeDisposable()

    override fun onServiceRegistered() {
        compositeDisposable += dogDataSource.getDogs().subscribeBy { dogs ->
            dogListRelay.accept(OptionalWrapper(dogs))
        }
    }

    override fun onServiceUnregistered() {
        compositeDisposable.clear()
    }

    override fun toBundle(): StateBundle = StateBundle().apply {
        putInt("firstVisibleItemIndex", lazyListState.firstVisibleItemIndex)
        putInt("firstVisibleItemScrollOffset", lazyListState.firstVisibleItemScrollOffset)
    }

    override fun fromBundle(bundle: StateBundle?) {
        bundle?.run {
            lazyListState = LazyListState(
                getInt("firstVisibleItemIndex"),
                getInt("firstVisibleItemScrollOffset")
            )
        }
    }
}
