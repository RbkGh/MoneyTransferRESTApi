package com.revolut.repository;

import com.revolut.domain.AccountEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:46
 */
public class AccountReposityDefaultImpl implements AccountRepository {


    private EntityManager entityManager;

    @Inject
    public AccountReposityDefaultImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AccountEntity saveAccount(AccountEntity accountEntity) {
        entityManager.getTransaction().begin();
        entityManager.persist(accountEntity);
        entityManager.getTransaction().commit();
        return getAccountByEmail(accountEntity.getEmailAddress());
    }

    @Override
    public AccountEntity getAccountById(Long id) {
        return null;
    }

    @Override
    public AccountEntity getAccountByEmail(String emailAddress) {
        Query query = entityManager.createQuery("from " + AccountEntity.class.getName() + " acc where acc.emailAddress = ?1");
        query.setParameter(1, emailAddress);
        return (AccountEntity) query.getSingleResult();
    }

    @Override
    public List<AccountEntity> getAllAccounts() {
        Query query = entityManager.createQuery("from " + AccountEntity.class.getName() + " a");

        List<AccountEntity> accountEntities = query.getResultList();

        accountEntities.forEach(accountEntity -> System.out.println("\nname = " + accountEntity.getName()));
        return accountEntities;
    }

    @Override
    public void deleteAccount(Long id) {

    }
}
