package com.example.quiz6.other_answer

import kotlin.random.Random

fun main() {

    val probabilities = mapOf(
        1 to 0.001,
        2 to 0.023,
        3 to 0.13,
        4 to 0.18,
        5 to 0.25
    )

    var remainingPrizes = mutableMapOf(
        1 to 1,
        2 to 1,
        3 to 2,
        4 to 5,
        5 to 11
    )

    while (remainingPrizes.isNotEmpty()) {
        val randomNum = Random.nextDouble()
        var cumulativeProbability = 0.0
        var chosenPrize: Int? = null

        for ((prize, probability) in probabilities) {
            if (remainingPrizes[prize] ?: 0 > 0) {
                cumulativeProbability += probability
                if (randomNum <= cumulativeProbability) {
                    chosenPrize = prize
                    break
                }
            }
        }

        if (chosenPrize != null) {
            val count = remainingPrizes[chosenPrize]!!
            if (count > 1) {
                remainingPrizes[chosenPrize] = count - 1
            } else {
                remainingPrizes.remove(chosenPrize)
            }
            println("抽到 $chosenPrize 號獎 目前尚未抽取的獎勵為 ${remainingPrizes.flatMap { (key, value) -> List(value) { key } }}")
        } else {
            println("沒有抽到任何獎項")
        }
    }
    println("禮物箱內沒有禮物")
}