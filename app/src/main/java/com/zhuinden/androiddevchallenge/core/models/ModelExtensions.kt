package com.zhuinden.androiddevchallenge.core.models

import com.zhuinden.androiddevchallenge.data.models.Dog

fun Dog.contentDescription(): String = run {
    val dog = this
    "${dog.name}, ${dog.determinedSex.name}, ${dog.breed}, ${dog.age} years old"
}