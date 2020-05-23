package com.kafedra.exam.filter

import com.google.inject.Singleton
import javax.servlet.*

@Singleton
class CharsetFilter : Filter {

    override fun init(config: FilterConfig) {}

    override fun doFilter(request: ServletRequest, response: ServletResponse, next: FilterChain) {
        request.characterEncoding = "UTF-8"
        response.contentType = "text/html; charset=UTF-8"
        response.characterEncoding = "UTF-8"
        next.doFilter(request, response)
    }

    override fun destroy() {}
}
