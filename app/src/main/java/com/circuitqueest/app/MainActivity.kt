package com.circuitqueest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.circuitqueest.app.navigation.CircuitQueestNavGraph
import com.circuitqueest.app.ui.theme.CircuitQueestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var blueprintMode by rememberSaveable { mutableStateOf(false) }
            CircuitQueestTheme(blueprintMode = blueprintMode) {
                CircuitQueestNavGraph(
                    onToggleBlueprint = { blueprintMode = !blueprintMode },
                    blueprintMode = blueprintMode
                )
            }
        }
    }
}
