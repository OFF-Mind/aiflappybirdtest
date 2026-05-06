package com.offmind.aiflappybird

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.offmind.aiflappybird.designsystem.components.CogwingPanel
import com.offmind.aiflappybird.designsystem.components.CogwingPrimaryButton
import com.offmind.aiflappybird.designsystem.components.PanelStyle
import com.offmind.aiflappybird.designsystem.theme.CogwingSpacing

@Composable
fun WelcomeScreen(onStartGame: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        CogwingPanel(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(CogwingSpacing.sp4),
            style = PanelStyle.BrassPlate,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(CogwingSpacing.sp3),
            ) {
                Text(
                    text = "AI Flappy Bird",
                    style = MaterialTheme.typography.headlineMedium,
                )
                CogwingPrimaryButton(
                    text = "START GAME",
                    onClick = onStartGame,
                )
            }
        }
    }
}
