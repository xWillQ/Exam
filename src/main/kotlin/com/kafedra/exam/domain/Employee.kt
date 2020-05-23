package com.kafedra.exam.domain

import com.google.gson.annotations.Expose

class Employee {
    @Expose var id = 0
    @Expose var name = ""
    @Expose var departmentId = 0 // Only for deserialization
    var version: Long = 0
}