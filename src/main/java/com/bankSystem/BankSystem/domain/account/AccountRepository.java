package com.bankSystem.BankSystem.domain.account;

import com.bankSystem.BankSystem.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Page<Account> findByUser(User user, Pageable pageable);
}
