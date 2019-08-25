package com.revolut.repository;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.domain.AccountEntity;
import com.revolut.module.AppModule;

import javax.persistence.EntityManager;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:46
 */
public class AccountReposityDefaultImpl implements AccountRepository {

    Injector injector = Guice.createInjector(new AppModule());

    @Override
    public AccountEntity saveAccount(AccountEntity accountEntity) {
        EntityManager entityManager = injector.getInstance(EntityManager.class);

        entityManager.getTransaction().begin();
        entityManager.persist(accountEntity);
        entityManager.getTransaction().commit();
        return null;
    }

    @Override
    public AccountEntity getAccountById(Long id) {
        return null;
    }

    @Override
    public void deleteAccount(Long id) {

    }
}
