package com.rosary.mysteries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rosary.mysteries.ui.RosaryNavigation
import com.rosary.mysteries.ui.theme.RosaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RosaryTheme {
                RosaryNavigation()
            }
        }
    }
}
