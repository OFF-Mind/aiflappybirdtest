package com.offmind.aiflappybird.data.repository

import com.offmind.aiflappybird.domain.repository.ScoreRepository

class ScoreRepositoryImpl : ScoreRepository {

    private var bestScore: Int = 0

    override fun getBestScore(): Int = bestScore

    override fun saveBestScore(score: Int) {
        if (score > bestScore) {
            bestScore = score
        }
    }
}
