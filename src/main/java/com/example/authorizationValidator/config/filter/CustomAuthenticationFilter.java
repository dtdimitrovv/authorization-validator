package com.example.authorizationValidator.config.filter;

import com.example.authorizationValidator.entity.BaseEntity;
import com.example.authorizationValidator.repository.AuthenticationEntityRepository;
import com.example.authorizationValidator.security.AuthenticatedUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.authorizationValidator.constant.HeaderConstant.X_USER_ID_HEADER_NAME;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticatedUser<? extends BaseEntity> authenticatedUser;

    private final AuthenticationEntityRepository<? extends BaseEntity, Long> authenticationEntityRepository;

    public CustomAuthenticationFilter(AuthenticatedUser<? extends BaseEntity> authenticatedUser, AuthenticationEntityRepository<? extends BaseEntity, Long> authenticationEntityRepository) {
        this.authenticatedUser = authenticatedUser;
        this.authenticationEntityRepository = authenticationEntityRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var userId = Long.valueOf(request.getHeader(X_USER_ID_HEADER_NAME));
            var entity = authenticationEntityRepository.findById(userId).orElse(null);
            authenticatedUser.setValue(entity);
        } catch (NumberFormatException ignored) {
        }
        finally {
            filterChain.doFilter(request, response);
        }
    }
}
