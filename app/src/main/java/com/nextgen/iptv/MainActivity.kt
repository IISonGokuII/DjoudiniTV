package com.nextgen.iptv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nextgen.iptv.navigation.AppNavigation
import com.nextgen.iptv.presentation.common.isTvDevice
import com.nextgen.iptv.presentation.theme.AppTheme
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
    val navController = rememberNavController()
    val isTv = isTvDevice()
    
    AppNavigation(
        navController = navController,
        modifier = Modifier.fillMaxSize(),
        isTv = isTv
    )
}
