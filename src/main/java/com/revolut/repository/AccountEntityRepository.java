package com.revolut.repository;

import com.revolut.domain.AccountEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:42
 */
public interface AccountEntityRepository {

    AccountEntity saveAccount(AccountEntity accountEntity);

    AccountEntity getAccountById(Long id);

    AccountEntity getAccountByEmail(String emailAddress);

    List<AccountEntity> getAllAccounts();

    void deleteAccount(Long id);

    boolean doesAccountExistById(Long id);

    void updateUserAccountBalance(AccountEntity accountEntity, BigDecimal bigDecimal) throws Exception;
}
