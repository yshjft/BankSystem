package com.bankSystem.BankSystem.domain.accountLog;

import com.bankSystem.BankSystem.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {
    Page<AccountLog> findByAccount(Account account, Pageable pageable);
}
