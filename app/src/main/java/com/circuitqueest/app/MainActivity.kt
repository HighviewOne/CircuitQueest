package com.circuitqueest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.circuitqueest.app.navigation.CircuitQueestNavGraph
import com.circuitqueest.app.ui.theme.CircuitQueestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CircuitQueestTheme {
                CircuitQueestNavGraph()
            }
        }
    }
}
