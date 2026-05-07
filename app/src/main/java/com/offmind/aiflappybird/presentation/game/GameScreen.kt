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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
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
import com.offmind.aiflappybird.designsystem.theme.SootDark
import com.offmind.aiflappybird.domain.model.GameState
import kotlin.math.floor
import kotlin.random.Random

private data class BackgroundLayer(
    val speed: Float,
    val alpha: Float,
    val blurRadius: Float,
)

private data class BackgroundTube(
    val baseX: Float,
    val heightFraction: Float,
)

private const val BACKGROUND_TUBE_COUNT = 5
private const val BACKGROUND_TUBE_SPACING = 0.4f
private const val BACKGROUND_TUBE_WIDTH = 0.10f
private const val BACKGROUND_LEFT_BOUND = -0.4f

private val BACKGROUND_LAYERS = listOf(
    BackgroundLayer(speed = 0.08f, alpha = 0.22f, blurRadius = 14f),
    BackgroundLayer(speed = 0.15f, alpha = 0.32f, blurRadius = 9f),
    BackgroundLayer(speed = 0.22f, alpha = 0.42f, blurRadius = 5f),
)

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val backgroundTubes = remember {
        BACKGROUND_LAYERS.mapIndexed { layerIdx, _ ->
            val random = Random(layerIdx * 1009L + 7L)
            List(BACKGROUND_TUBE_COUNT) { i ->
                BackgroundTube(
                    baseX = i * BACKGROUND_TUBE_SPACING,
                    heightFraction = 0.45f + random.nextFloat() * 0.30f,
                )
            }
        }
    }

    var elapsedTime by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(uiState.gameState) {
        if (uiState.gameState == GameState.Running) {
            var lastFrame = withFrameNanos { it }
            while (true) {
                val frame = withFrameNanos { it }
                elapsedTime += (frame - lastFrame) / 1_000_000_000f
                lastFrame = frame
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { viewModel.onTap() }
                }
        ) {
            drawRect(color = SootDark, size = size)

            if (size.width > 0 && size.height > 0 && uiState.gameState != GameState.Idle) {
                val rangeLength = BACKGROUND_TUBE_COUNT * BACKGROUND_TUBE_SPACING
                val tubeWidthPx = size.width * BACKGROUND_TUBE_WIDTH

                BACKGROUND_LAYERS.forEachIndexed { layerIdx, layer ->
                    val scroll = elapsedTime * layer.speed
                    val tubes = backgroundTubes[layerIdx]

                    tubes.forEach { tube ->
                        val shifted = tube.baseX - scroll - BACKGROUND_LEFT_BOUND
                        val wrappedShifted = shifted - floor(shifted / rangeLength) * rangeLength
                        val wrapped = wrappedShifted + BACKGROUND_LEFT_BOUND

                        val tubePx = size.width * wrapped
                        val tubeHeightPx = size.height * tube.heightFraction

                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = Copper.copy(alpha = layer.alpha).toArgb()
                                maskFilter = android.graphics.BlurMaskFilter(
                                    layer.blurRadius,
                                    android.graphics.BlurMaskFilter.Blur.NORMAL
                                )
                            }
                            canvas.nativeCanvas.drawRect(
                                tubePx,
                                size.height - tubeHeightPx,
                                tubePx + tubeWidthPx,
                                size.height,
                                paint
                            )
                        }
                    }
                }
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
