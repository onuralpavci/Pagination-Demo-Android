package com.example.paginationdemo.utils

import javax.inject.Inject

data class FetchResponse @Inject constructor(val people: List<PersonResponse>, val next: String?)
