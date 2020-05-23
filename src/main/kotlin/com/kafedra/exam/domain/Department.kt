package com.kafedra.exam.domain

import com.google.gson.annotations.Expose

class Department {
    @Expose var id = 0
    @Expose var title = ""
    @Expose var number = ""
    var version: Long = 0
}