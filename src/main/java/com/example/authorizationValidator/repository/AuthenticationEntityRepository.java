package com.example.authorizationValidator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/*
  Implement once per project
 */
@NoRepositoryBean
public interface AuthenticationEntityRepository<T, ID> extends JpaRepository<T, ID> {
}
