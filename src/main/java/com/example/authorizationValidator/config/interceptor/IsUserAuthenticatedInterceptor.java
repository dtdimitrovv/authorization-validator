package com.example.authorizationValidator.config.interceptor;

import com.example.authorizationValidator.security.AuthenticatedUser;
import com.example.authorizationValidator.security.IsAuthenticated;
import com.example.authorizationValidator.entity.BaseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
public class IsUserAuthenticatedInterceptor implements HandlerInterceptor {

    private final AuthenticatedUser<? extends BaseEntity> authenticatedUser;

    public IsUserAuthenticatedInterceptor(AuthenticatedUser<? extends BaseEntity> authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        var isClassAnnotationPresent =
                Optional.of(handlerMethod.getMethod())
                        .map(Method::getDeclaringClass)
                        .map(declaringClass -> declaringClass.isAnnotationPresent(IsAuthenticated.class))
                        .orElse(Boolean.FALSE);
        var isMethodAnnotationPresent = handlerMethod.hasMethodAnnotation(IsAuthenticated.class);

        if (!isMethodAnnotationPresent && !isClassAnnotationPresent) {
            return true;
        }

        return Optional.ofNullable(this.authenticatedUser.getValue())
                .map(u -> Boolean.TRUE)
                .orElseGet(() -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    return false;
                });
    }
}
