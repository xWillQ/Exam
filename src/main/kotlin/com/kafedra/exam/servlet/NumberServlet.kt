package com.kafedra.exam.servlet

import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.exam.dao.EmployeeDao
import com.kafedra.exam.dao.NumberDao
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class NumberServlet : HttpServlet() {
    @Inject
    lateinit var dao: NumberDao
    @Inject
    lateinit var gsonProvider: GSONProvider

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val gson = gsonProvider.get()
        val id = request.getParameter("employeeId").toIntOrNull()
        val json = gson.toJson(dao.getNumbersByEmployee(id?:0))
        response.writer.write(json)
    }
}