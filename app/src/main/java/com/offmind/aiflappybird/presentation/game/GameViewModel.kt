package com.offmind.aiflappybird.presentation.game

import androidx.lifecycle.ViewModel
import com.offmind.aiflappybird.domain.model.Bird
import com.offmind.aiflappybird.domain.model.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun onTap() {
        when (_uiState.value.gameState) {
            GameState.Idle -> _uiState.update { it.copy(gameState = GameState.Running) }
            GameState.Running -> {
                val newY = (_uiState.value.bird.y - 0.05f).coerceIn(0f, 1f)
                _uiState.update { it.copy(bird = Bird(y = newY)) }
            }
            GameState.GameOver -> _uiState.update {
                it.copy(gameState = GameState.Idle, bird = Bird())
            }
        }
    }
}
