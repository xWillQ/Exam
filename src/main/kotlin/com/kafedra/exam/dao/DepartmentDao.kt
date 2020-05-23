package com.kafedra.exam.dao

import com.google.inject.Inject
import com.kafedra.aaapp.di.HibernateProvider
import com.kafedra.exam.domain.Department

class DepartmentDao {
    @Inject lateinit var sessionProvider: HibernateProvider

    fun getDepartments() : List<Department> {
        val session = sessionProvider.get().openSession()
        val query = session.createQuery("FROM Department", Department::class.java)
        val departments = query.resultList
        session.close()
        return departments
    }

    fun deleteDepartment(id: Int) {
        // TODO
    }

    fun editDepartment(dep: Department) {
        // TODO
//        val session = sessionProvider.get().openSession()
//        session.beginTransaction()
//
//        session.update(dep)
//
//        session.transaction.commit()
//        session.close()
    }

    fun addDepartment(dep: Department) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()

        session.save(dep)

        session.transaction.commit()
        session.close()
    }
}