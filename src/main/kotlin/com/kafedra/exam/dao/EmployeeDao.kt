package com.kafedra.exam.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.exam.domain.Employee

class EmployeeDao {
    @Inject
    lateinit var sessionProvider: HibernateProvider

    fun getEmployeesByDepartment(depId: Int): List<Employee> {
        val session = sessionProvider.get().openSession()
        val query = session.createQuery("FROM Employee WHERE departmentId = $depId", Employee::class.java)
        val employees = query.resultList
        session.close()
        return employees
    }

    fun searchBy(params: Map<String, String>): List<Employee> {
        val session = sessionProvider.get().openSession()
        var queryString = ""
        var and = false
        for (pair in params) {
            if (pair.value == null || pair.value == "") continue
            if (and) queryString += " AND"
            if (pair.key == "departmentId" && pair.value == "0") queryString += " ${pair.key} = null"
            else queryString += " ${pair.key} = '${pair.value}'"
            and = true
        }
        if (queryString != "") queryString = " WHERE $queryString"
        val query = session.createQuery("FROM Employee $queryString", Employee::class.java)
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

    fun editEmployee(emp: Employee) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        val prev = session.get(Employee::class.java, emp.id)
        if (prev == null) {
            session.close()
            return
        }

        if (emp.name != "") prev.name = emp.name
        if (emp.departmentId != null) prev.departmentId = if (emp.departmentId == 0) null else emp.departmentId
        session.update(prev)

        session.transaction.commit()
        session.close()
    }

    fun deleteEmployee(id: Int) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        session.createQuery("DELETE FROM Number WHERE employeeId = $id").executeUpdate()
        session.createQuery("DELETE FROM Employee WHERE id = $id").executeUpdate()

        session.transaction.commit()
        session.close()
    }
}