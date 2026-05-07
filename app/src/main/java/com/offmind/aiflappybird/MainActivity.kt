package com.offmind.aiflappybird

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.offmind.aiflappybird.designsystem.theme.CogwingTheme
import com.offmind.aiflappybird.presentation.game.GameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show greeting toast once when the activity is created
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()

        enableEdgeToEdge()
        setContent {
            CogwingTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GameScreen()
                }
            }
        }
    }
}
