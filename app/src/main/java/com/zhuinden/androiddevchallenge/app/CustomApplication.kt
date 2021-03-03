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
package com.zhuinden.androiddevchallenge.app

import android.app.Application
import com.zhuinden.androiddevchallenge.data.datasource.DogDataSource
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestackextensions.servicesktx.add
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlin.random.Random

class CustomApplication : Application() {
    lateinit var globalServices: GlobalServices
        private set

    override fun onCreate() {
        super.onCreate()

        val random = Random.Default

        val mainThreadScheduler = AndroidSchedulers.mainThread()

        globalServices = GlobalServices.builder()
            .add(DogDataSource(random, mainThreadScheduler))
            .build()
    }
}
