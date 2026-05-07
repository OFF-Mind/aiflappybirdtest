package com.offmind.aiflappybird.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.offmind.aiflappybird.data.repository.ScoreRepositoryImpl
import com.offmind.aiflappybird.domain.model.Bird
import com.offmind.aiflappybird.domain.model.GameState
import com.offmind.aiflappybird.domain.model.Obstacle
import com.offmind.aiflappybird.domain.repository.ScoreRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(
    private val scoreRepository: ScoreRepository = ScoreRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState(bestScore = scoreRepository.getBestScore()))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var gameLoopJob: Job? = null
    private var lastFrameTime = 0L
    private var timeSinceLastSpawn = 0f
    private var playingTime = 0f
    private var nextObstacleId = 0
    private var nextBackgroundObstacleId = -1000

    private companion object {
        const val TICK_INTERVAL_MS = 16L
        const val MAX_DELTA_TIME = 0.05f
        const val GRAVITY = 1.8f
        const val FLAP_VELOCITY = -0.65f
        const val BASE_OBSTACLE_SPEED = 0.30f
        const val BASE_SPAWN_INTERVAL = 1.6f
        const val MIN_SPAWN_INTERVAL = 0.95f
        const val GAP_HEIGHT = 0.30f
        const val TOP_MARGIN = 0.15f
        const val BOTTOM_MARGIN = 0.15f
        const val BACKGROUND_SPEED_RATIO = 0.4f
        const val BACKGROUND_OBSTACLE_SPACING = 0.6f
    }

    fun onTap() {
        when (_uiState.value.gameState) {
            GameState.Idle -> {
                startGame()
            }
            GameState.Running -> {
                flap()
            }
            GameState.GameOver -> {
                restart()
            }
        }
    }

    private fun startGame() {
        if (gameLoopJob?.isActive == true) return

        _uiState.update {
            it.copy(
                gameState = GameState.Running,
                backgroundObstacles = createInitialBackgroundObstacles()
            )
        }
        lastFrameTime = System.currentTimeMillis()
        timeSinceLastSpawn = 0f
        playingTime = 0f
        nextObstacleId = 0
        nextBackgroundObstacleId = -1000

        gameLoopJob = viewModelScope.launch {
            while (_uiState.value.gameState == GameState.Running) {
                val currentTime = System.currentTimeMillis()
                val deltaTime = ((currentTime - lastFrameTime) / 1000f).coerceAtMost(MAX_DELTA_TIME)
                lastFrameTime = currentTime

                updateGame(deltaTime)

                delay(TICK_INTERVAL_MS)
            }
        }
    }

    private fun flap() {
        _uiState.update {
            it.copy(bird = it.bird.copy(velocityY = FLAP_VELOCITY))
        }
    }

    private fun restart() {
        stopGameLoop()
        _uiState.update {
            GameUiState(
                gameState = GameState.Idle,
                bird = Bird(),
                obstacles = emptyList(),
                score = 0,
                bestScore = scoreRepository.getBestScore()
            )
        }
    }

    private fun updateGame(deltaTime: Float) {
        _uiState.update { state ->
            if (state.gameState != GameState.Running) return@update state

            playingTime += deltaTime
            timeSinceLastSpawn += deltaTime

            var updatedState = state.copy(
                bird = updateBird(state.bird, deltaTime),
                obstacles = updateObstacles(state.obstacles, deltaTime),
                backgroundObstacles = updateBackgroundObstacles(state.backgroundObstacles, deltaTime)
            )

            val spawnInterval = calculateSpawnInterval()
            if (timeSinceLastSpawn >= spawnInterval) {
                updatedState = updatedState.copy(
                    obstacles = updatedState.obstacles + spawnObstacle()
                )
                timeSinceLastSpawn = 0f
            }

            updatedState = checkScoring(updatedState)

            if (checkCollision(updatedState.bird, updatedState.obstacles)) {
                gameOver(updatedState.score)
                return@update updatedState.copy(gameState = GameState.GameOver)
            }

            updatedState
        }
    }

    private fun updateBird(bird: Bird, deltaTime: Float): Bird {
        val newVelocityY = bird.velocityY + GRAVITY * deltaTime
        val newY = (bird.y + newVelocityY * deltaTime).coerceIn(0f, 1f)
        return bird.copy(y = newY, velocityY = newVelocityY)
    }

    private fun updateObstacles(obstacles: List<Obstacle>, deltaTime: Float): List<Obstacle> {
        val speed = calculateObstacleSpeed()
        return obstacles
            .map { it.copy(x = it.x - speed * deltaTime) }
            .filter { it.x + it.width > 0f }
    }

    private fun spawnObstacle(): Obstacle {
        val minGapCenter = TOP_MARGIN + GAP_HEIGHT / 2
        val maxGapCenter = 1f - BOTTOM_MARGIN - GAP_HEIGHT / 2
        val gapCenter = Random.nextFloat() * (maxGapCenter - minGapCenter) + minGapCenter

        return Obstacle(
            id = nextObstacleId++,
            x = 1f,
            gapTop = gapCenter - GAP_HEIGHT / 2,
            gapBottom = gapCenter + GAP_HEIGHT / 2
        )
    }

    private fun createInitialBackgroundObstacles(): List<Obstacle> {
        val obstacles = mutableListOf<Obstacle>()
        val random = Random(42)
        var xPosition = 0.2f

        while (xPosition <= 1.2f) {
            val minGapCenter = TOP_MARGIN + GAP_HEIGHT / 2
            val maxGapCenter = 1f - BOTTOM_MARGIN - GAP_HEIGHT / 2
            val gapCenter = random.nextFloat() * (maxGapCenter - minGapCenter) + minGapCenter

            obstacles.add(
                Obstacle(
                    id = nextBackgroundObstacleId++,
                    x = xPosition,
                    gapTop = gapCenter - GAP_HEIGHT / 2,
                    gapBottom = gapCenter + GAP_HEIGHT / 2
                )
            )
            xPosition += BACKGROUND_OBSTACLE_SPACING
        }

        return obstacles
    }

    private fun updateBackgroundObstacles(obstacles: List<Obstacle>, deltaTime: Float): List<Obstacle> {
        val speed = calculateObstacleSpeed() * BACKGROUND_SPEED_RATIO
        val updated = obstacles.map { it.copy(x = it.x - speed * deltaTime) }.toMutableList()

        while (updated.firstOrNull()?.let { it.x + it.width < -0.2f } == true) {
            updated.removeAt(0)

            val lastX = updated.lastOrNull()?.x ?: 0.2f
            val newX = lastX + BACKGROUND_OBSTACLE_SPACING

            val random = Random(nextBackgroundObstacleId)
            val minGapCenter = TOP_MARGIN + GAP_HEIGHT / 2
            val maxGapCenter = 1f - BOTTOM_MARGIN - GAP_HEIGHT / 2
            val gapCenter = random.nextFloat() * (maxGapCenter - minGapCenter) + minGapCenter

            updated.add(
                Obstacle(
                    id = nextBackgroundObstacleId++,
                    x = newX,
                    gapTop = gapCenter - GAP_HEIGHT / 2,
                    gapBottom = gapCenter + GAP_HEIGHT / 2
                )
            )
        }

        return updated
    }

    private fun checkScoring(state: GameUiState): GameUiState {
        var newScore = state.score
        val updatedObstacles = state.obstacles.map { obstacle ->
            if (!obstacle.passed && obstacle.x + obstacle.width < state.bird.x) {
                newScore++
                obstacle.copy(passed = true)
            } else {
                obstacle
            }
        }
        return state.copy(score = newScore, obstacles = updatedObstacles)
    }

    private fun checkCollision(bird: Bird, obstacles: List<Obstacle>): Boolean {
        if (bird.y + bird.radius >= 1f) {
            return true
        }

        for (obstacle in obstacles) {
            val birdLeft = bird.x - bird.radius
            val birdRight = bird.x + bird.radius
            val obstacleLeft = obstacle.x
            val obstacleRight = obstacle.x + obstacle.width

            if (birdRight > obstacleLeft && birdLeft < obstacleRight) {
                val birdTop = bird.y - bird.radius
                val birdBottom = bird.y + bird.radius

                if (birdTop < obstacle.gapTop || birdBottom > obstacle.gapBottom) {
                    return true
                }
            }
        }

        return false
    }

    private fun calculateObstacleSpeed(): Float {
        val speedIncrease = playingTime * 0.02f
        return (BASE_OBSTACLE_SPEED + speedIncrease).coerceAtMost(BASE_OBSTACLE_SPEED * 1.8f)
    }

    private fun calculateSpawnInterval(): Float {
        val intervalDecrease = playingTime * 0.02f
        return (BASE_SPAWN_INTERVAL - intervalDecrease).coerceAtLeast(MIN_SPAWN_INTERVAL)
    }

    private fun gameOver(finalScore: Int) {
        if (finalScore > scoreRepository.getBestScore()) {
            scoreRepository.saveBestScore(finalScore)
            _uiState.update { it.copy(bestScore = finalScore) }
        }
    }

    private fun stopGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopGameLoop()
    }
}
