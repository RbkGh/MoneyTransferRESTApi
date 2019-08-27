package com.revolut.repository;

import com.revolut.domain.AccountEntity;
import com.revolut.domain.AccountTransactionEntity;
import com.revolut.domain.TransactionStatus;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

/**
 * author: acerbk
 * Date: 2019-08-26
 * Time: 21:08
 */
public class AccountTransactionRepositoryDefaultImpl implements AccountTransactionRepository {

    private EntityManager entityManager;

    @Inject
    public AccountTransactionRepositoryDefaultImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createAccountTransaction(AccountTransactionEntity accountTransactionEntity,
                                         TransactionStatus transactionStatus,
                                         String reason) throws Exception {
        try {
            entityManager.getTransaction().begin();
            //set date of Transaction,reason and transaction status
            accountTransactionEntity.setDateOfTransaction(new Date(System.currentTimeMillis()));
            accountTransactionEntity.setTransactionStatus(transactionStatus);
            accountTransactionEntity.setReason(reason);

            entityManager.persist(accountTransactionEntity);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    @Override
    public List<AccountTransactionEntity> getAccountTransactionsByAccountId(Long accountId) {
        Query query = entityManager.createQuery("from " + AccountTransactionEntity.class.getName() + " a");

        List<AccountTransactionEntity> accountTransactionEntities = query.getResultList();

        accountTransactionEntities.forEach(accountTransactionEntity -> System.out.println("\ntransaction entity = " + accountTransactionEntity.toString()));
        return accountTransactionEntities;
    }
}
