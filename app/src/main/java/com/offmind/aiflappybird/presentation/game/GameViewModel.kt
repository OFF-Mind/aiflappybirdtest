package com.offmind.aiflappybird.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.offmind.aiflappybird.domain.model.Bird
import com.offmind.aiflappybird.domain.model.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var physicsJob: Job? = null

    private companion object {
        const val TICK_INTERVAL_MS = 16L
        const val GRAVITY = 1.2f
        const val FLAP_IMPULSE = -0.35f
    }

    fun onTap() {
        when (_uiState.value.gameState) {
            GameState.Idle -> {
                _uiState.update { it.copy(gameState = GameState.Running) }
                startPhysicsLoop()
            }
            GameState.Running -> {
                _uiState.update {
                    it.copy(bird = it.bird.copy(velocityY = FLAP_IMPULSE))
                }
            }
            GameState.GameOver -> {
                stopPhysicsLoop()
                _uiState.update {
                    it.copy(gameState = GameState.Idle, bird = Bird())
                }
            }
        }
    }

    private fun startPhysicsLoop() {
        stopPhysicsLoop()
        physicsJob = viewModelScope.launch {
            while (_uiState.value.gameState == GameState.Running) {
                val deltaTime = TICK_INTERVAL_MS / 1000f
                _uiState.update { state ->
                    if (state.gameState != GameState.Running) return@update state

                    val bird = state.bird
                    val newVelocityY = bird.velocityY + GRAVITY * deltaTime
                    val newY = (bird.y + newVelocityY * deltaTime).coerceIn(0f, 1f)

                    state.copy(bird = bird.copy(y = newY, velocityY = newVelocityY))
                }
                delay(TICK_INTERVAL_MS)
            }
        }
    }

    private fun stopPhysicsLoop() {
        physicsJob?.cancel()
        physicsJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopPhysicsLoop()
    }
}
