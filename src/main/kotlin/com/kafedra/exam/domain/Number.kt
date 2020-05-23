package com.kafedra.exam.domain

import com.google.gson.annotations.Expose
import com.kafedra.exam.PhoneType

class Number {
    var id = 0
    @Expose
    var type: PhoneType? = null
    @Expose var number = ""
    @Expose var employee: Employee? = null
    var version: Long = 0
}