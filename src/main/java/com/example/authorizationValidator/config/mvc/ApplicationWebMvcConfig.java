package com.example.authorizationValidator.config.mvc;

import com.example.authorizationValidator.config.argumentResolver.AuthenticatedUserArgumentResolver;
import com.example.authorizationValidator.config.interceptor.IsUserAuthenticatedInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ApplicationWebMvcConfig implements WebMvcConfigurer {

    private final IsUserAuthenticatedInterceptor isUserAuthenticatedInterceptor;
    private final AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;

    public ApplicationWebMvcConfig(IsUserAuthenticatedInterceptor isUserAuthenticatedInterceptor, AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver) {
        this.isUserAuthenticatedInterceptor = isUserAuthenticatedInterceptor;
        this.authenticatedUserArgumentResolver = authenticatedUserArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(isUserAuthenticatedInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedUserArgumentResolver);
    }
}