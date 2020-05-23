package com.kafedra.exam.servlet

import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.exam.dao.DepartmentDao
import com.kafedra.exam.domain.Department
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class DepartmentServlet : HttpServlet() {
    @Inject
    lateinit var dao: DepartmentDao

    @Inject
    lateinit var gsonProvider: GSONProvider

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val gson = gsonProvider.get()
        val json = gson.toJson(dao.getDepartments())
        response.writer.write(json)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val json = req.reader.readLine()
        val gson = gsonProvider.get()
        val dep = gson.fromJson(json, Department::class.java)

        when {
            req.getParameter("add") != null -> dao.addDepartment(dep)
            req.getParameter("update") != null -> dao.editDepartment(dep)
            req.getParameter("delete") != null -> dao.deleteDepartment(dep.id)
        }
    }
}
