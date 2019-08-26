package com.revolut.repository;

import com.revolut.domain.AccountEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

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
        AccountEntity accountEntity = null;
        try {
            Query query = entityManager.createQuery("from " + AccountEntity.class.getName() + " acc where acc.id = ?1");
            query.setParameter(1, id);
            accountEntity = (AccountEntity) query.getSingleResult();
        } catch (Exception ex) {
            return accountEntity;
        }
        return accountEntity;
    }

    @Override
    public AccountEntity getAccountByEmail(String emailAddress) {
        AccountEntity accountEntity = null;
        try {
            Query query = entityManager.createQuery("from " + AccountEntity.class.getName() + " acc where acc.emailAddress = ?1");
            query.setParameter(1, emailAddress);
            accountEntity = (AccountEntity) query.getSingleResult();
        } catch (Exception ex) {
            return accountEntity;
        }
        return accountEntity;
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

        Query query = entityManager.createQuery("delete from " + AccountEntity.class.getName() + " acc where acc.id = ?1");
        query.setParameter(1, id);
        int number = query.executeUpdate();
        System.out.println("deleted =" + number);
    }
}
