package com.example.paginationdemo.utils

import javax.inject.Inject

data class PersonResponse @Inject constructor(val id: Int, val fullName: String)
