package com.kafedra.exam.domain

import com.google.gson.annotations.Expose

class Employee {
    @Expose var id = 0
    @Expose var name = ""
    var department: Department? = null
    var version: Long = 0
}