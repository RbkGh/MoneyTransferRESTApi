package com.revolut.service;

import com.revolut.domain.AccountEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.repository.AccountEntityRepository;
import com.revolut.repository.AccountTransactionRepository;
import com.revolut.util.JsonParser;
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
    private AccountEntityRepository accountEntityRepository;
    @Mock
    private AccountTransactionRepository accountTransactionRepository;
    @Mock
    private JsonParser jsonParser;
    private AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountService(accountEntityRepository, accountTransactionRepository, jsonParser);
    }

    @Test
    public void create_account_expect_status_code_201() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmailAddress("email@gmail.com");
        accountEntity.setName("rodney");

        Mockito.when(accountEntityRepository.saveAccount(accountEntity)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(accountEntity);

        assertEquals(endpointOperationResponsePayload.getStatusCode(), 201);
    }

    @Test
    public void create_account_when_no_email_present_expect_status_code_400() {
        AccountEntity accountEntity = new AccountEntity();

        Mockito.when(accountEntityRepository.saveAccount(accountEntity)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(accountEntity);

        assertTrue(endpointOperationResponsePayload.getStatusCode() == 400);
    }

    @Test
    public void create_account_when_email_address_exist_but_empty_expect_status_code_400() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmailAddress("");
        accountEntity.setName("");

        Mockito.when(accountEntityRepository.saveAccount(accountEntity)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(accountEntity);

        assertEquals(endpointOperationResponsePayload.getStatusCode(), 400);
    }

    @Test
    public void create_account_when_email_address_format_is_incorrect_expect_status_code_400() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("ff");
        accountEntity.setEmailAddress("bb");

        Mockito.when(accountEntityRepository.saveAccount(accountEntity)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(accountEntity);

        assertEquals(endpointOperationResponsePayload.getStatusCode(), 400);
    }

    @Test
    public void delete_account_expect_status_code_400() {

        long id = 3;
        AccountEntity accountEntity = new AccountEntity(id);
        accountEntity.setName("ff");
        accountEntity.setEmailAddress("bb");

        Mockito.when(accountEntityRepository.getAccountById(id)).thenReturn(accountEntity);
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.deleteAccountById(String.valueOf(id));

        assertEquals(endpointOperationResponsePayload.getStatusCode(), 204);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_account_when_account_data_is_null_expect_null_pointer_exception_thrown() {
        EndpointOperationResponsePayload endpointOperationResponsePayload = accountService.createAccount(null);
        assertEquals(endpointOperationResponsePayload.getStatusCode(), 400);
    }
}
