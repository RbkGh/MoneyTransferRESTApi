package com.revolut.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Singleton;

import com.revolut.controllers.AccountController;
import com.revolut.controllers.AccountControllerImpl;
import com.revolut.repository.AccountEntityRepository;
import com.revolut.repository.AccountEntityReposityDefaultImpl;
import com.revolut.repository.AccountTransactionRepository;
import com.revolut.repository.AccountTransactionRepositoryDefaultImpl;
import com.revolut.util.JsonParser;
import com.revolut.util.JsonParserImpl;
import com.revolut.util.ResponseCreator;
import com.revolut.util.ResponseCreatorImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * author: acerbk
 * Date: 2019-08-25
 * Time: 03:59
 * Google Guice Framework DI Configuration
 */
public class AppModule extends AbstractModule {

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<EntityManager>();

    @Override
    protected void configure() {
        bind(AccountController.class).to(AccountControllerImpl.class);
        bind(AccountEntityRepository.class).to(AccountEntityReposityDefaultImpl.class);
        bind(JsonParser.class).to(JsonParserImpl.class);
        bind(ResponseCreator.class).to(ResponseCreatorImpl.class);
        bind(AccountTransactionRepository.class).to(AccountTransactionRepositoryDefaultImpl.class);
    }

    @Provides
    @Singleton
    public EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("db-manager");
    }

    @Provides
    public EntityManager createEntityManager(
            EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = ENTITY_MANAGER_CACHE.get();
        if (entityManager == null) {
            ENTITY_MANAGER_CACHE.set(entityManager = entityManagerFactory
                    .createEntityManager());
        }
        return entityManager;
    }
}
