package com.example.paginationdemo.utils

import android.os.Handler
import android.os.Looper
import javax.inject.Inject
import kotlin.math.min

class CustomDataSource @Inject constructor() {

    private object Constants {
        val peopleCountRange: ClosedRange<Int> = 100..200 // lower bound must be > 0, upper bound must be > lower bound
        val fetchCountRange: ClosedRange<Int> = 5..20 // lower bound must be > 0, upper bound must be > lower bound
        val lowWaitTimeRange: ClosedRange<Double> =
            0.0..0.3 // lower bound must be >= 0.0, upper bound must be > lower bound
        val highWaitTimeRange: ClosedRange<Double> =
            1.0..2.0 // lower bound must be >= 0.0, upper bound must be > lower bound
        const val errorProbability = 0.05 // must be > 0.0
        const val backendBugTriggerProbability = 0.3 // must be > 0.0
        const val emptyFirstResultsProbability = 0.1 // must be > 0.0
    }

    init {
        initializeData()
    }

    public fun fetch(next: String?, completionHandler: FetchCompletionHandler) {
        val processResult = processRequest(next)

        Handler(Looper.getMainLooper()).postDelayed({
            completionHandler(processResult.fetchResponse, processResult.fetchError)
        }, (processResult.waitTime * 1000).toLong())
    }

    private fun initializeData() {
        if (people.isNotEmpty()) {
            return
        }
        val newPeople: ArrayList<PersonResponse> = arrayListOf()
        val peopleCount: Int = RandomUtils.generateRandomInt(range = Constants.peopleCountRange)
        for (index in 0 until peopleCount) {
            val personResponse = PersonResponse(id = index + 1, fullName = PeopleGen.generateRandomFullName())
            newPeople.add(personResponse)
        }
        people = newPeople.shuffled()
    }

    private fun processRequest(next: String?): ProcessResult {
        var error: FetchError? = null
        var response: FetchResponse? = null
        val isError = RandomUtils.roll(probability = Constants.errorProbability)
        val waitTime: Double
        if (isError) {
            waitTime = RandomUtils.generateRandomDouble(range = Constants.lowWaitTimeRange)
            error = FetchError(errorDescription = "Internal Server Error")
        } else {
            waitTime = RandomUtils.generateRandomDouble(range = Constants.highWaitTimeRange)
            val fetchCount = RandomUtils.generateRandomInt(range = Constants.fetchCountRange)
            val peopleCount = people.size
            val nextIntValue = try {
                next!!.toInt()
            } catch (ex: Exception) {
                null
            }
            if (next != null && (nextIntValue == null || nextIntValue < 0)) {
                error = FetchError(errorDescription = "Parameter error")
            } else {
                val endIndex: Int = min(peopleCount, fetchCount + (nextIntValue ?: 0))
                val beginIndex: Int = if (next == null) 0 else min(nextIntValue!!, endIndex)
                var responseNext: String? = if (endIndex >= peopleCount) null else endIndex.toString()
                var fetchedPeople: ArrayList<PersonResponse> =
                    ArrayList(people.subList(beginIndex, endIndex)) // begin ile end ayni olunca bos donuyor mu?
                if (beginIndex > 0 && RandomUtils.roll(probability = Constants.backendBugTriggerProbability)) {
                    fetchedPeople.add(0, people[beginIndex - 1])
                } else if (beginIndex == 0 && RandomUtils.roll(probability = Constants.emptyFirstResultsProbability)) {
                    fetchedPeople = arrayListOf()
                    responseNext = null
                }
                response = FetchResponse(people = fetchedPeople, next = responseNext)
            }
        }
        return ProcessResult(response, error, waitTime)
    }

    companion object {
        private var people: List<PersonResponse> = listOf()
    }
}