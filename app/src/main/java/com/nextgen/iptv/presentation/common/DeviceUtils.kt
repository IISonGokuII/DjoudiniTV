package com.nextgen.iptv.presentation.common

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun Context.isTvDevice(): Boolean {
    return packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK) ||
            (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= 
            Configuration.SCREENLAYOUT_SIZE_XLARGE
}

@Composable
fun isTvDevice(): Boolean {
    return LocalContext.current.isTvDevice()
}
