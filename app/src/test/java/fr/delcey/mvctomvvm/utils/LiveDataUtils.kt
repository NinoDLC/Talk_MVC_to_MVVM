package fr.delcey.mvctomvvm.utils

import androidx.lifecycle.LiveData

/**
 * When a return value is expected
 */
fun <T> LiveData<T>.getValueForTesting(): T {
    observeForever {}

    return value ?: throw AssertionError("LiveData value is null")
}

/**
 * When no emission is expected
 */
fun LiveData<*>.observeForTesting() {
    observeForever {}

    if (value != null) {
        throw AssertionError("LiveData emitted a value. If a value is expected, use getValueForTesting() instead")
    }
}