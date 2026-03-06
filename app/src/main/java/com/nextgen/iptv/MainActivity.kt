package com.nextgen.iptv

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nextgen.iptv.ui.navigation.AppNavigation
import com.nextgen.iptv.presentation.common.isTvDevice
import com.nextgen.iptv.presentation.onboarding.OnboardingScreen
import com.nextgen.iptv.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
                DjoudiniTVApp()
            }
        }
    }
}

@Composable
private fun DjoudiniTVApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val navController = rememberNavController()
    val isTv = isTvDevice()
    
    // Check if onboarding is complete
    var showOnboarding by remember {
        mutableStateOf(!isOnboardingComplete(context))
    }
    
    if (showOnboarding) {
        OnboardingScreen(
            onComplete = {
                markOnboardingComplete(context)
                showOnboarding = false
            }
        )
    } else {
        AppNavigation(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            isTv = isTv
        )
    }
}

private fun isOnboardingComplete(context: Context): Boolean {
    val prefs = context.getSharedPreferences("djoundinitv_prefs", Context.MODE_PRIVATE)
    return prefs.getBoolean("onboarding_complete", false)
}

private fun markOnboardingComplete(context: Context) {
    context.getSharedPreferences("djoundinitv_prefs", Context.MODE_PRIVATE)
        .edit()
        .putBoolean("onboarding_complete", true)
        .apply()
}
