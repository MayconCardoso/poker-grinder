package com.mctech.pokergrinder.architecture.extensions

import android.os.Build
import android.os.Bundle

/**
 * Used to wrap the deserialization
 */
@Suppress("DEPRECATION")
inline fun <reified T: java.io.Serializable> Bundle?.deserialize(key: String): T? {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        this?.getSerializable(key) as? T
    } else {
        this?.getSerializable(key, T::class.java)
    }
}