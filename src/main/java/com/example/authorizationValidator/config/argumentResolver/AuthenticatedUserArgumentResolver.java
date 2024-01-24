package com.example.authorizationValidator.config.argumentResolver;

import com.example.authorizationValidator.entity.BaseEntity;
import com.example.authorizationValidator.security.AuthenticatedUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthenticatedUser<? extends BaseEntity> authenticatedUser;

    public AuthenticatedUserArgumentResolver(AuthenticatedUser<? extends BaseEntity> authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().isAnnotationPresent(AuthenticatedPrincipal.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        return Optional.of(parameter)
                .map(MethodParameter::getParameterType)
                .filter(parameterType -> parameterType.equals(this.authenticatedUser.getValue().getClass()))
                .map(clazz -> this.authenticatedUser.getValue())
                .orElseThrow(() ->
                        new IllegalArgumentException("The annotated differs from the one stored in the AuthenticatedUser instance"));
    }
}
