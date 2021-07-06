package com.celonis.challenge.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.ForwardedHeaderFilter

@OpenAPIDefinition(
    info = Info(
        title = "Task Service API",
        version = "1.0",
        description = "API for create, edit, cancel and execute task",
        contact = Contact(
            name = "Challenge Java",
            email = "manoj_expjava@yahoo.co.in",
            url = "https://gitlab.com"
        ))
)
class OpenApiConfig {
    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter? {
        return ForwardedHeaderFilter()
    }
}
