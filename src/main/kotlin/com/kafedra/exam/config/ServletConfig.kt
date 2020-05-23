package com.kafedra.exam.config

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule

class ServletConfig : GuiceServletContextListener() {
    override fun getInjector(): Injector = Guice.createInjector(object : ServletModule() {
        override fun configureServlets() {
            super.configureServlets()

        }
    })
}
