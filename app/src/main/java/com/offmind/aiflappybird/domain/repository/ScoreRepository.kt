package com.offmind.aiflappybird.domain.repository

interface ScoreRepository {
    fun getBestScore(): Int
    fun saveBestScore(score: Int)
}
