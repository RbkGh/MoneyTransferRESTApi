package com.revolut.service;

import com.revolut.domain.AccountEntity;
import com.revolut.domain.AccountTransactionEntity;
import com.revolut.domain.TransactionStatus;
import com.revolut.repository.AccountEntityRepository;
import com.revolut.repository.AccountTransactionRepository;
import com.revolut.util.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * author: acerbk
 * Date: 2019-08-27
 * Time: 03:59
 */
public class AccountTransactionServiceTest {

    @Mock
    private AccountTransactionRepository accountTransactionRepository;
    @Mock
    private AccountEntityRepository accountEntityRepository;

    @Mock
    private JsonParser jsonParser;

    private AccountTransactionService accountTransactionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountTransactionService = new AccountTransactionService(accountEntityRepository,
                accountTransactionRepository, jsonParser);
    }

    @Test
    public void get_all_money_transfer_transactions_for_account_expect_200_status() {

        String accountId = "3";

        AccountEntity accountEntity = new AccountEntity(Long.valueOf(accountId));
        accountEntity.setEmailAddress("email@gmail.com");
        accountEntity.setName("rodney");

        Mockito.when(accountEntityRepository.doesAccountExistById(Long.valueOf(accountId))).thenReturn(true);

        Mockito.when(accountTransactionRepository.getAccountTransactionsByAccountId(Long.valueOf(accountId)))
                .thenReturn(
                        Arrays.asList(
                                new AccountTransactionEntity(Long.valueOf(accountId)
                                        , 4L
                                        , new BigDecimal("2500")
                                        , TransactionStatus.SUCCESS
                                        , new Date(System.currentTimeMillis()))));

        assertEquals(200, accountTransactionService.getAccountTransactionsByAccountId(accountId).getStatusCode());
    }
}
