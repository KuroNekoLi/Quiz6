package com.example.quiz6.other_answer

fun main(){
    calculateSeries(10000)
}

fun calculateSeries(N: Int): Int {
    var sum = 0
    for (i in 1..N) {
        if (i % 2 == 1) {
            sum += i
        } else {
            sum -= i
        }
    }
    return sum
}