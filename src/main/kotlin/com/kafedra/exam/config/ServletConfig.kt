package com.kafedra.exam.config

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule
import com.kafedra.exam.filter.CharsetFilter
import com.kafedra.exam.servlet.DepartmentServlet
import com.kafedra.exam.servlet.EmployeeServlet
import com.kafedra.exam.servlet.HelloServlet
import com.kafedra.exam.servlet.NumberServlet
import org.flywaydb.core.Flyway

class ServletConfig : GuiceServletContextListener() {
    override fun getInjector(): Injector = Guice.createInjector(object : ServletModule() {
        override fun configureServlets() {
            super.configureServlets()
            filter("/*").through(CharsetFilter::class.java)

            serve("/hello").with(HelloServlet::class.java)

            serve("/ajax/department").with(DepartmentServlet::class.java)
            serve("/ajax/employee").with(EmployeeServlet::class.java)
            serve("/ajax/number").with(NumberServlet::class.java)

            val url = System.getenv("JDBC_DATABASE_URL") ?: "jdbc:h2:./aaa;MV_STORE=FALSE"
            val login = System.getenv("JDBC_DATABASE_USERNAME") ?: "se"
            val pass = System.getenv("JDBC_DATABASE_PASSWORD") ?: ""
            Flyway.configure().dataSource(url, login, pass).locations("classpath:migrations").load().migrate()
        }
    })
}
