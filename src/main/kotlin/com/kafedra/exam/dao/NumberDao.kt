package com.kafedra.exam.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.exam.domain.Department
import com.kafedra.exam.domain.Number

class NumberDao {
    @Inject
    lateinit var sessionProvider: HibernateProvider

    fun getNumbersByEmployee(empId: Int) : List<Number> {
        val session = sessionProvider.get().openSession()
        val query = session.createQuery("FROM Number WHERE employeeId = $empId", Number::class.java)
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
}