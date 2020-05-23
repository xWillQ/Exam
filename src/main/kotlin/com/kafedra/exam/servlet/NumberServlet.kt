package com.kafedra.exam.servlet

import com.google.gson.reflect.TypeToken
import com.google.inject.Inject
import com.google.inject.Singleton
import com.kafedra.aaapp.di.GSONProvider
import com.kafedra.exam.dao.NumberDao
import com.kafedra.exam.domain.Number
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
        if (request.getParameter("types") != null) {
            response.writer.write(gson.toJson(dao.getTypes()))
            return
        }
        val id = request.getParameter("employeeId").toIntOrNull()
        val json = gson.toJson(dao.getNumbersByEmployee(id ?: 0))
        response.writer.write(json)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val json = req.reader.readLine()
        val gson = gsonProvider.get()
        val numb = gson.fromJson(json, Number::class.java)

        when {
            (req.getParameter("add") != null && numb.type != null) -> dao.addNumber(numb)
            req.getParameter("update") != null -> dao.editNumber(numb)
            req.getParameter("delete") != null -> dao.deleteNumber(numb.id)
            req.getParameter("search") != null -> {
                val type = object : TypeToken<Map<String, String>>() {}.type
                val map = gson.fromJson<Map<String, String>>(json, type)
                resp.writer.write(gson.toJson(dao.searchBy(map)))
            }
        }

    }
}