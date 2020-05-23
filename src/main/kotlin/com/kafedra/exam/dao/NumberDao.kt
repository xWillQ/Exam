package com.kafedra.exam.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.exam.domain.Number

class NumberDao {
    @Inject
    lateinit var sessionProvider: HibernateProvider

    fun getNumbersByEmployee(empId: Int): List<Number> {
        val session = sessionProvider.get().openSession()
        val query = session.createQuery("FROM Number WHERE employeeId = $empId", Number::class.java)
        val numbers = query.resultList
        session.close()
        return numbers
    }

    fun searchBy(params: Map<String, String>): List<Number> {
        val session = sessionProvider.get().openSession()
        var queryString = ""
        var and = false
        for (pair in params) {
            if (pair.value == null || pair.value == "") continue
            if (and) queryString += " AND"
            queryString += " ${pair.key} = '${pair.value}'"
            and = true
        }
        if (queryString != "") queryString = " WHERE $queryString"
        val query = session.createQuery("FROM Number $queryString", Number::class.java)
        val numbers = query.resultList
        session.close()
        return numbers
    }

    fun addNumber(numb: Number) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        session.save(numb)

        session.transaction.commit()
        session.close()
    }

    fun editNumber(numb: Number) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        val prev = session.get(Number::class.java, numb.id)
        if (prev == null) {
            session.close()
            return
        }

        if (numb.type != null) prev.type = numb.type
        if (numb.number != "") prev.number = numb.number
        session.update(prev)

        session.transaction.commit()
        session.close()
    }

    fun deleteNumber(id: Int) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        session.createQuery("DELETE FROM Number WHERE id = $id").executeUpdate()

        session.transaction.commit()
        session.close()
    }
}