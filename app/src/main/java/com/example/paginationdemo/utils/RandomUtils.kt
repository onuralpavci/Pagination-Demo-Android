package com.example.paginationdemo.utils

import com.example.paginationdemo.utils.FetchError
import com.example.paginationdemo.utils.FetchResponse
import kotlin.random.Random

typealias FetchCompletionHandler = (FetchResponse?, FetchError?) -> Unit

// Utils
object RandomUtils {

    fun generateRandomInt(range: ClosedRange<Int>): Int = Random.nextInt(range.start, range.endInclusive)

    fun generateRandomDouble(range: ClosedRange<Double>): Double = Random.nextDouble(range.start, range.endInclusive)

    fun roll(probability: Double): Boolean {
        val random = Random.nextDouble(0.0, 1.0)
        return random <= probability
    }
}
