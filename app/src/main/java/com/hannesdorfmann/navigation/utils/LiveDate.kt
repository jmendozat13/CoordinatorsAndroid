package com.hannesdorfmann.navigation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


inline fun <T> LiveData<T>.subscribe(owner: LifecycleOwner, crossinline subscriber: (T) -> Unit) {
    observe(owner, { value -> subscriber(value!!) })
}