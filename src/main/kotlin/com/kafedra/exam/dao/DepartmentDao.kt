package com.kafedra.exam.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.exam.domain.Department

class DepartmentDao {
    @Inject
    lateinit var sessionProvider: HibernateProvider

    fun getDepartments(): List<Department> {
        val session = sessionProvider.get().openSession()
        val query = session.createQuery("FROM Department", Department::class.java)
        val departments = query.resultList
        session.close()
        return departments
    }

    fun deleteDepartment(id: Int) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        session.createQuery("UPDATE Employee SET departmentId = null WHERE departmentId = $id").executeUpdate()
        session.createQuery("DELETE FROM Department WHERE id = $id").executeUpdate()

        session.transaction.commit()
        session.close()
    }

    fun editDepartment(dep: Department) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        val prev = session.get(Department::class.java, dep.id)
        if (prev == null) {
            session.close()
            return
        }

        if (dep.title != "") prev.title = dep.title
        if (dep.number != "") prev.number = dep.number
        session.update(prev)

        session.transaction.commit()
        session.close()
    }

    fun addDepartment(dep: Department) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        session.save(dep)

        session.transaction.commit()
        session.close()
    }
}