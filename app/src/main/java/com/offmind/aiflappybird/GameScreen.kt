package com.offmind.aiflappybird

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

enum class GameState {
    Idle,
    Running,
    GameOver
}

@Composable
fun GameScreen() {
    var gameState by remember { mutableStateOf(GameState.Idle) }
    var playerY by remember { mutableFloatStateOf(0.5f) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    when (gameState) {
                        GameState.Idle -> {
                            println("HUI state=Idle -> Running")
                            gameState = GameState.Running
                        }
                        GameState.Running -> {
                            playerY = (playerY - 0.05f).coerceIn(0f, 1f)
                            println("HUI playerY=$playerY")
                        }
                        GameState.GameOver -> {
                            println("HUI state=GameOver -> Idle")
                            gameState = GameState.Idle
                            playerY = 0.5f
                        }
                    }
                }
            }
    ) {
        // Background
        drawRect(color = Color(0xFF4EC0CA), size = size)

        val playerRadius = size.width * 0.05f
        val playerX = size.width * 0.25f
        val playerYPx = size.height * playerY

        // Player (circle)
        drawCircle(
            color = Color(0xFFFFD700),
            radius = playerRadius,
            center = Offset(playerX, playerYPx)
        )

        // Obstacle (rectangle — single pipe gap pair)
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

        // State label (debug overlay)
        if (gameState == GameState.Idle || gameState == GameState.GameOver) {
            val labelText = if (gameState == GameState.Idle) "TAP TO START" else "GAME OVER — TAP TO RETRY"
            drawRect(
                color = Color(0x88000000),
                topLeft = Offset(size.width * 0.1f, size.height * 0.45f),
                size = Size(size.width * 0.8f, size.height * 0.1f)
            )
        }
    }
}
