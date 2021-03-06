package com.kafedra.exam.servlet

import com.google.gson.reflect.TypeToken
import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.exam.dao.DepartmentDao
import com.kafedra.exam.dao.EmployeeDao
import com.kafedra.exam.domain.Employee
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class EmployeeServlet : HttpServlet() {
    @Inject
    lateinit var dao: EmployeeDao

    @Inject
    lateinit var depDao: DepartmentDao

    @Inject
    lateinit var gsonProvider: GSONProvider

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val gson = gsonProvider.get()
        val id = request.getParameter("departmentId").toIntOrNull()
        val json = gson.toJson(dao.getEmployeesByDepartment(id ?: 0))
        response.writer.write(json)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val json = req.reader.readLine()
        val gson = gsonProvider.get()
        val emp = gson.fromJson(json, Employee::class.java)

        when {
            req.getParameter("add") != null -> dao.addEmployee(emp)
            req.getParameter("update") != null -> dao.editEmployee(emp)
            req.getParameter("delete") != null -> dao.deleteEmployee(emp.id)
            req.getParameter("search") != null -> {
                val type = object : TypeToken<Map<String, String>>() {}.type
                val map = gson.fromJson<Map<String, String>>(json, type)
                resp.writer.write(gson.toJson(dao.searchBy(map)))
            }
        }
    }
}