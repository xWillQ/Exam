package com.kafedra.exam.domain

import com.google.gson.annotations.Expose

class Employee {
    var id = 0
    @Expose var name = ""
    @Expose var department: Department? = null
    var version: Long = 0
}