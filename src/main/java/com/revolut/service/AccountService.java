package com.revolut.service;

import com.revolut.domain.AccountEntity;
import com.revolut.domain.AccountTransactionEntity;
import com.revolut.domain.TransactionStatus;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.model.ErrorOperationWithReasonPayload;
import com.revolut.model.SuccessfulOperationWithEmptyBodyPayload;
import com.revolut.model.SuccessfulOperationWithJSONBodyResponsePayload;
import com.revolut.repository.AccountEntityRepository;
import com.revolut.repository.AccountTransactionRepository;
import com.revolut.util.JsonParser;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.math.BigDecimal;
import java.util.*;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 19:11
 */
public class AccountService {

    private final AccountEntityRepository accountEntityRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private JsonParser jsonParser;

    @Inject
    AccountService(AccountEntityRepository accountEntityRepository,
                   AccountTransactionRepository accountTransactionRepository,
                   JsonParser jsonParser) {
        this.accountEntityRepository = accountEntityRepository;
        this.accountTransactionRepository = accountTransactionRepository;
        this.jsonParser = jsonParser;
    }

    public EndpointOperationResponsePayload createAccount(AccountEntity accountEntity) {

        Set<ConstraintViolation<AccountEntity>> constraintViolations =
                Validation.buildDefaultValidatorFactory().getValidator().validate(accountEntity);

        if (constraintViolations.size() == 0) {
            this.accountEntityRepository.saveAccount(accountEntity);
            return new SuccessfulOperationWithEmptyBodyPayload(201);
        }
        return new ErrorOperationWithReasonPayload(400, constraintViolations.iterator().next().getMessage());
    }

    public EndpointOperationResponsePayload createAccountTransaction(String senderAccountId, AccountTransactionEntity accountTransactionEntity) throws Exception {
        Set<ConstraintViolation<AccountTransactionEntity>> constraintViolations =
                Validation.buildDefaultValidatorFactory().getValidator().validate(accountTransactionEntity);

        //set url id of customer to sending account id
        accountTransactionEntity.setSendingAccountId(Long.valueOf(senderAccountId));

        if (constraintViolations.size() == 0) {
            Long receiverAccountId = accountTransactionEntity.getReceivingAccountId();
            //check if both accounts exist in database.
            if (accountEntityRepository.doesAccountExistById(Long.valueOf(senderAccountId)) && accountEntityRepository.doesAccountExistById(receiverAccountId)) {

                if (canUserInitiateMoneyTransfer(accountTransactionEntity)) {

                    try {
                        updateUserAccountBalancesInDatastore(accountTransactionEntity);
                    } catch (Exception ex) {
                        return new ErrorOperationWithReasonPayload(500, "Could not complete request");
                    }
                    //now save the transaction entity as successful once we reach here.
                    //createAccountTransactionInDatastore(accountTransactionEntity, TransactionStatus.SUCCESS, "");
                    return new SuccessfulOperationWithEmptyBodyPayload(201);
                }
                String reasonForFailure = "Not Enough Balance to initiate transaction";
                createAccountTransactionInDatastore(accountTransactionEntity, TransactionStatus.FAILED, reasonForFailure);
                return new ErrorOperationWithReasonPayload(403, reasonForFailure);//we dont want to expose financial information with "not enough balance"
            }
            return new ErrorOperationWithReasonPayload(404, "Account with id = " + senderAccountId + " does not exist.");
        }
        String errorMessage = constraintViolations.iterator().next().getMessage();
        return new ErrorOperationWithReasonPayload(400, errorMessage);
    }

    public EndpointOperationResponsePayload getAllAccounts() {
        List<AccountEntity> accountEntities = this.accountEntityRepository.getAllAccounts();
        if (accountEntities.isEmpty())
            return new SuccessfulOperationWithJSONBodyResponsePayload(200, "[]");
        return new SuccessfulOperationWithJSONBodyResponsePayload(200, this.jsonParser.toJSONString(accountEntities));
    }

    public EndpointOperationResponsePayload getAccountById(String id) {
        AccountEntity accountEntity = this.accountEntityRepository.getAccountById(Long.valueOf(id));
        if (Objects.nonNull(accountEntity))
            return new SuccessfulOperationWithJSONBodyResponsePayload(200, this.jsonParser.toJSONString(accountEntity));
        return new ErrorOperationWithReasonPayload(404, "Account with id=" + id + " not found.");
    }

    public EndpointOperationResponsePayload deleteAccountById(String id) {
        if (Objects.nonNull(accountEntityRepository.getAccountById(Long.valueOf(id)))) {

            accountEntityRepository.deleteAccount(Long.valueOf(id));
            return new SuccessfulOperationWithEmptyBodyPayload(204);
        }
        return new ErrorOperationWithReasonPayload(404, "Account with id =" + id + " not found.");
    }

    private boolean canUserInitiateMoneyTransfer(AccountTransactionEntity accountTransactionEntity) {
        AccountEntity senderAccount =
                accountEntityRepository.getAccountById(accountTransactionEntity.getSendingAccountId());

        BigDecimal transactionAmount =
                accountTransactionEntity.getTransactionAmount();

        //if comparisonResult =-1,then it's less than value ,0=equal,1=greater
        int comparisonResult =
                senderAccount.getAccountBalance().compareTo(transactionAmount);
        if (comparisonResult >= 0)
            return true;
        return false;
    }

    void updateUserAccountBalancesInDatastore(AccountTransactionEntity accountTransactionEntity) {
        AccountEntity senderAccount =
                accountEntityRepository.getAccountById(accountTransactionEntity.getSendingAccountId());

        AccountEntity receiverAccount =
                accountEntityRepository.getAccountById(accountTransactionEntity.getReceivingAccountId());

        BigDecimal transactionAmount = accountTransactionEntity.getTransactionAmount();


        AccountEntity updatedSenderAccountBalance = debitAccountEntity(senderAccount, transactionAmount);
        AccountEntity updatedRecieverAccountBalance = creditAccountEntity(receiverAccount, transactionAmount);

        accountEntityRepository
                .updateAccountBalancesAndTransactionLog(updatedSenderAccountBalance
                        , updatedRecieverAccountBalance
                        , accountTransactionEntity);
    }

    private AccountEntity creditAccountEntity(AccountEntity accountEntity, BigDecimal amountToCredit) {

        BigDecimal currentBalanceBeforeAddition = accountEntity.getAccountBalance();

        BigDecimal currentBalanceAfterAddition = currentBalanceBeforeAddition.add(amountToCredit);

        accountEntity.setAccountBalance(currentBalanceAfterAddition);

        return accountEntity;
    }

    private AccountEntity debitAccountEntity(AccountEntity accountEntity, BigDecimal amountToDebit) {

        BigDecimal currentBalanceBeforeDebit = accountEntity.getAccountBalance();

        BigDecimal currentBalanceAfterDebit = currentBalanceBeforeDebit.subtract(amountToDebit);

        accountEntity.setAccountBalance(currentBalanceAfterDebit);

        return accountEntity;
    }

    private void createAccountTransactionInDatastore(AccountTransactionEntity accountTransactionEntity, TransactionStatus transactionStatus, String reason) throws Exception {
        accountTransactionRepository.createAccountTransaction(accountTransactionEntity, transactionStatus, reason);
    }
}
