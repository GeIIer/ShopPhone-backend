package com.example.demo.authorization.repositories;

import com.example.demo.authorization.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsAccountEntityByEmail(String email);

    AccountEntity findByEmail(String email);
    AccountEntity save(AccountEntity registration);

    @Override
    long count();
}
