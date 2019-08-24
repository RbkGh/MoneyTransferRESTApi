package com.revolut.service;

import com.revolut.domain.AccountEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:30
 */
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    private AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void create_account_expect_status_code_201() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmailAddress("email@gmail.com");
        accountEntity.setName("rodney");

        Mockito.when(accountRepository.saveAccount(accountEntity)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(accountEntity);


        assertEquals(endpointOperationResponsePayload.getStatusCode(), 201);
    }

    @Test
    public void create_account_when_no_email_present_expect_status_code_400() {
        AccountEntity accountEntity = new AccountEntity();

        Mockito.when(accountRepository.saveAccount(accountEntity)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(accountEntity);


        assertTrue(endpointOperationResponsePayload.getStatusCode() == 400);
    }

    @Test
    public void create_account_when_email_address_exist_but_empty_expect_status_code_400() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmailAddress("");
        accountEntity.setName("");
        Mockito.when(accountRepository.saveAccount(accountEntity)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(accountEntity);


        assertEquals(endpointOperationResponsePayload.getStatusCode(), 400);
    }

    @Test
    public void create_account_when_email_address_format_is_incorrect_expect_status_code_400() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("ff");
        accountEntity.setEmailAddress("bb");

        Mockito.when(accountRepository.saveAccount(accountEntity)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(accountEntity);


        assertEquals(endpointOperationResponsePayload.getStatusCode(), 400);
    }
}
