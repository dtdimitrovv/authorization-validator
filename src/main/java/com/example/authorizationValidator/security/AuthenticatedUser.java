package com.example.authorizationValidator.security;

import com.example.authorizationValidator.entity.BaseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticatedUser<R extends BaseEntity> {
    private R value;

    public R getValue() {
        return value;
    }

    public void setValue(BaseEntity value) {
        this.value = (R) value;
    }
}
