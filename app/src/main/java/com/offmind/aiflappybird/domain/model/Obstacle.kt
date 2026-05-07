package com.offmind.aiflappybird.domain.model

data class Obstacle(
    val id: Int,
    val x: Float,
    val width: Float = 0.16f,
    val gapTop: Float,
    val gapBottom: Float,
    val passed: Boolean = false
)
