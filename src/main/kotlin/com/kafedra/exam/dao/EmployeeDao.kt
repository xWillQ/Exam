package com.kafedra.exam.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.exam.domain.Department
import com.kafedra.exam.domain.Employee

class EmployeeDao {
    @Inject
    lateinit var sessionProvider: HibernateProvider

    fun getEmployeesByDepartment(depId: Int) : List<Employee> {
        val session = sessionProvider.get().openSession()
        val query = session.createQuery("FROM Employee WHERE departmentId = $depId", Employee::class.java)
        val employees = query.resultList
        session.close()
        return employees
    }

    fun addEmployee(emp: Employee) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        session.save(emp)

        session.transaction.commit()
        session.close()
    }
}