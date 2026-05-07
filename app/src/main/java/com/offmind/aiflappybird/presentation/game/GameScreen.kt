package com.offmind.aiflappybird.presentation.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.offmind.aiflappybird.designsystem.components.CogwingPanel
import com.offmind.aiflappybird.designsystem.components.CogwingPrimaryButton
import com.offmind.aiflappybird.designsystem.components.CogwingScoreRow
import com.offmind.aiflappybird.designsystem.components.PanelStyle
import com.offmind.aiflappybird.designsystem.theme.Brass
import com.offmind.aiflappybird.designsystem.theme.CogwingSpacing
import com.offmind.aiflappybird.designsystem.theme.Copper
import com.offmind.aiflappybird.designsystem.theme.Soot
import com.offmind.aiflappybird.designsystem.theme.SootDark
import com.offmind.aiflappybird.designsystem.theme.SootLight
import com.offmind.aiflappybird.domain.model.GameState

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { viewModel.onTap() }
                }
        ) {
            drawRect(color = SootDark, size = size)

            val backgroundWidth = size.width * 2f
            val wrappedOffset = (uiState.backgroundOffset * size.width) % backgroundWidth
            val offsetX = -wrappedOffset

            for (i in 0..1) {
                val baseX = offsetX + i * backgroundWidth

                val hillPath1 = Path().apply {
                    moveTo(baseX, size.height)
                    lineTo(baseX, size.height * 0.7f)
                    quadraticTo(
                        baseX + backgroundWidth * 0.15f, size.height * 0.55f,
                        baseX + backgroundWidth * 0.3f, size.height * 0.65f
                    )
                    quadraticTo(
                        baseX + backgroundWidth * 0.45f, size.height * 0.75f,
                        baseX + backgroundWidth * 0.6f, size.height * 0.7f
                    )
                    lineTo(baseX + backgroundWidth, size.height * 0.7f)
                    lineTo(baseX + backgroundWidth, size.height)
                    close()
                }
                drawPath(
                    path = hillPath1,
                    color = Soot.copy(alpha = 0.6f)
                )

                val hillPath2 = Path().apply {
                    moveTo(baseX, size.height)
                    lineTo(baseX, size.height * 0.8f)
                    quadraticTo(
                        baseX + backgroundWidth * 0.25f, size.height * 0.72f,
                        baseX + backgroundWidth * 0.5f, size.height * 0.78f
                    )
                    quadraticTo(
                        baseX + backgroundWidth * 0.75f, size.height * 0.84f,
                        baseX + backgroundWidth, size.height * 0.8f
                    )
                    lineTo(baseX + backgroundWidth, size.height)
                    close()
                }
                drawPath(
                    path = hillPath2,
                    color = SootLight.copy(alpha = 0.4f)
                )

                drawCircle(
                    color = SootLight.copy(alpha = 0.3f),
                    radius = size.width * 0.08f,
                    center = Offset(baseX + backgroundWidth * 0.2f, size.height * 0.25f)
                )
                drawCircle(
                    color = SootLight.copy(alpha = 0.25f),
                    radius = size.width * 0.06f,
                    center = Offset(baseX + backgroundWidth * 0.7f, size.height * 0.35f)
                )
            }

            uiState.obstacles.forEach { obstacle ->
                val obstacleX = size.width * obstacle.x
                val obstacleWidth = size.width * obstacle.width
                val gapTopPx = size.height * obstacle.gapTop
                val gapBottomPx = size.height * obstacle.gapBottom

                drawRect(
                    color = Copper,
                    topLeft = Offset(obstacleX, 0f),
                    size = Size(obstacleWidth, gapTopPx)
                )
                drawRect(
                    color = Copper,
                    topLeft = Offset(obstacleX, gapBottomPx),
                    size = Size(obstacleWidth, size.height - gapBottomPx)
                )
            }

            val playerRadius = size.width * uiState.bird.radius
            val playerX = size.width * uiState.bird.x
            val playerYPx = size.height * uiState.bird.y

            drawCircle(
                color = Brass,
                radius = playerRadius,
                center = Offset(playerX, playerYPx)
            )
        }

        CogwingScoreRow(
            stats = listOf(
                "Score" to uiState.score.toString(),
                "Best" to uiState.bestScore.toString(),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(CogwingSpacing.sp4),
        )

        if (uiState.gameState == GameState.Idle || uiState.gameState == GameState.GameOver) {
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
                    val label = if (uiState.gameState == GameState.Idle) "Take Wing" else "Try Again"
                    CogwingPrimaryButton(text = label, onClick = viewModel::onTap)
                }
            }
        }
    }
}
