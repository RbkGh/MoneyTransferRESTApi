package com.revolut.repository;

import com.revolut.domain.AccountTransactionEntity;
import com.revolut.domain.TransactionStatus;

import java.util.List;

/**
 * author: acerbk
 * Date: 2019-08-26
 * Time: 21:04
 */
public interface AccountTransactionRepository {

    /**
     * create a transaction with a completed or failed transaction status
     *
     * @param accountTransactionEntity
     * @throws Exception
     */
    void createAccountTransaction(AccountTransactionEntity accountTransactionEntity,
                                  TransactionStatus transactionStatus,
                                  String reason) throws Exception;

    /**
     * get account transactions for specified account Id
     *
     * @param accountId
     * @return
     */
    List<AccountTransactionEntity> getAccountTransactionsByAccountId(Long accountId);

}
