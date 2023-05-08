package com.example.paginationdemo.utils

import javax.inject.Inject

data class ProcessResult @Inject constructor(val fetchResponse: FetchResponse?, val fetchError: FetchError?, val waitTime: Double)
