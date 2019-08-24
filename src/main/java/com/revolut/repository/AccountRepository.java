package com.revolut.repository;

import com.revolut.domain.AccountEntity;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:42
 */
public interface AccountRepository {

    AccountEntity saveAccount(AccountEntity accountEntity);

    AccountEntity getAccountById(Long id);

    void deleteAccount(Long id);
}
