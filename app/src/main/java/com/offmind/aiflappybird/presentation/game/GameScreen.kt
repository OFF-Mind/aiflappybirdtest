package com.offmind.aiflappybird.presentation.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.offmind.aiflappybird.domain.model.GameState

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { viewModel.onTap() }
            }
    ) {
        drawRect(color = Color(0xFF4EC0CA), size = size)

        val playerRadius = size.width * 0.05f
        val playerX = size.width * 0.25f
        val playerYPx = size.height * uiState.bird.y

        drawCircle(
            color = Color(0xFFFFD700),
            radius = playerRadius,
            center = Offset(playerX, playerYPx)
        )

        val obstacleX = size.width * 0.7f
        val obstacleWidth = size.width * 0.1f
        val gapTop = size.height * 0.35f
        val gapBottom = size.height * 0.55f

        drawRect(
            color = Color(0xFF228B22),
            topLeft = Offset(obstacleX, 0f),
            size = Size(obstacleWidth, gapTop)
        )
        drawRect(
            color = Color(0xFF228B22),
            topLeft = Offset(obstacleX, gapBottom),
            size = Size(obstacleWidth, size.height - gapBottom)
        )

        if (uiState.gameState == GameState.Idle || uiState.gameState == GameState.GameOver) {
            drawRect(
                color = Color(0x88000000),
                topLeft = Offset(size.width * 0.1f, size.height * 0.45f),
                size = Size(size.width * 0.8f, size.height * 0.1f)
            )
        }
    }
}
