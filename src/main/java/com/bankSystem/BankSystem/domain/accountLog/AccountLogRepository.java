package com.bankSystem.BankSystem.domain.accountLog;

import com.bankSystem.BankSystem.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {
    Page<AccountLog> findByAccount(Account account, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from AccountLog acl where acl.account.id = :accountId")
    void bulkDeleteByAccount(@Param("accountId") Long accountId);
}
