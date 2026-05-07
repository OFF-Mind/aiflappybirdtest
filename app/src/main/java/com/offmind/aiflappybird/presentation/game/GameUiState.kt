package com.offmind.aiflappybird.presentation.game

import com.offmind.aiflappybird.domain.model.Bird
import com.offmind.aiflappybird.domain.model.GameState
import com.offmind.aiflappybird.domain.model.Obstacle

data class GameUiState(
    val gameState: GameState = GameState.Idle,
    val bird: Bird = Bird(),
    val obstacles: List<Obstacle> = emptyList(),
    val backgroundObstacles: List<Obstacle> = emptyList(),
    val score: Int = 0,
    val bestScore: Int = 0
)
