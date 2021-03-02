package com.zhuinden.androiddevchallenge.utils

class OptionalWrapper<T>(val value: T?) {
    companion object {
        fun <T> absent(): OptionalWrapper<T> = OptionalWrapper(null)
    }
}