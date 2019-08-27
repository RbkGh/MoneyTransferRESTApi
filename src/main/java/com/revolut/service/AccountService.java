package com.revolut.service;

import com.revolut.domain.AccountEntity;
import com.revolut.domain.AccountTransactionEntity;
import com.revolut.domain.TransactionStatus;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.model.ErrorOperationWithReasonPayload;
import com.revolut.model.SuccessfulOperationWithEmptyBodyPayload;
import com.revolut.model.SuccessfulOperationWithJSONBodyResponsePayload;
import com.revolut.repository.AccountRepository;
import com.revolut.repository.AccountTransactionRepository;
import com.revolut.util.EmailValidator;
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

    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private JsonParser jsonParser;

    @Inject
    AccountService(AccountRepository accountRepository, AccountTransactionRepository accountTransactionRepository, JsonParser jsonParser) {
        this.accountRepository = accountRepository;
        this.accountTransactionRepository = accountTransactionRepository;
        this.jsonParser = jsonParser;
    }

    public EndpointOperationResponsePayload createAccount(AccountEntity accountEntity) {
        Map<Boolean, String> isAccountPropertiesValidMap = isAccountEntityPropertiesValid(accountEntity);
        if (isAccountPropertiesValidMap.containsKey(true)) {
            this.accountRepository.saveAccount(accountEntity);
            return new SuccessfulOperationWithEmptyBodyPayload(201);
        }
        return new ErrorOperationWithReasonPayload(400, isAccountPropertiesValidMap.get(false));
    }

    public EndpointOperationResponsePayload createAccountTransaction(String senderAccountId, AccountTransactionEntity accountTransactionEntity) throws Exception {
        Set<ConstraintViolation<AccountTransactionEntity>> constraintViolations =
                Validation.buildDefaultValidatorFactory().getValidator().validate(accountTransactionEntity);

        //set url id of customer to sending account id
        accountTransactionEntity.setSendingAccountId(Long.valueOf(senderAccountId));

        if (constraintViolations.size() == 0) {
            //execute here
            Long receiverAccountId = accountTransactionEntity.getReceivingAccountId();
            //check if both accounts exist in database.
            if (accountRepository.doesAccountExistById(Long.valueOf(senderAccountId)) && accountRepository.doesAccountExistById(receiverAccountId)) {

                if (canUserInitiateMoneyTransfer(accountTransactionEntity)) {

                    try {
                        updateUserAccountBalancesAfterTransaction(accountTransactionEntity);
                    } catch (Exception ex) {
                        return new ErrorOperationWithReasonPayload(500, "Could not complete request");
                    }
                    //now save the transaction entity as successful once we reach here.
                    createAccountTransactionInDatastore(accountTransactionEntity, TransactionStatus.SUCCESS, "");
                    return new SuccessfulOperationWithEmptyBodyPayload(201);

                }
                return new ErrorOperationWithReasonPayload(403, "Forbidden");//we dont want to expose financial information with "not enough balance"
            }
            return new ErrorOperationWithReasonPayload(404, "Account with id = " + senderAccountId + " does not exist.");
        }
        String errorMessage = constraintViolations.iterator().next().getMessage();
        return new ErrorOperationWithReasonPayload(400, errorMessage);
    }

    public EndpointOperationResponsePayload getAllAccounts() {
        List<AccountEntity> accountEntities = this.accountRepository.getAllAccounts();
        if (accountEntities.isEmpty())
            return new SuccessfulOperationWithJSONBodyResponsePayload(200, "[]");
        return new SuccessfulOperationWithJSONBodyResponsePayload(200, this.jsonParser.toJSONString(accountEntities));
    }

    public EndpointOperationResponsePayload getAccountById(String id) {
        AccountEntity accountEntity = this.accountRepository.getAccountById(Long.valueOf(id));
        if (Objects.nonNull(accountEntity))
            return new SuccessfulOperationWithJSONBodyResponsePayload(200, this.jsonParser.toJSONString(accountEntity));
        return new ErrorOperationWithReasonPayload(404, "Account with id=" + id + " not found.");
    }

    public EndpointOperationResponsePayload deleteAccountById(String id) {
        if (Objects.nonNull(accountRepository.getAccountById(Long.valueOf(id)))) {

            accountRepository.deleteAccount(Long.valueOf(id));
            return new SuccessfulOperationWithEmptyBodyPayload(204);
        }
        return new ErrorOperationWithReasonPayload(404, "Account with id =" + id + " not found.");
    }

    /**
     * validate account properties,
     * at each point,map returned will contain either a {@link Boolean#TRUE} or {@link Boolean#FALSE} only as key.
     *
     * @param accountEntity
     * @return
     */
    private Map<Boolean, String> isAccountEntityPropertiesValid(AccountEntity accountEntity) {

        //is name field null or empty?
        if (Objects.isNull(accountEntity.getName()) || accountEntity.getName().isEmpty())
            return new HashMap<Boolean, String>() {{
                put(false, "Name is required");
            }};

        //is email address field null or empty?
        if (Objects.isNull(accountEntity.getEmailAddress()) || accountEntity.getEmailAddress().isEmpty())
            return new HashMap<Boolean, String>() {{
                put(false, "Email address is required");
            }};

        //is email format valid?
        if (!EmailValidator.isValidEmailAddress(accountEntity.getEmailAddress()))
            return new HashMap<Boolean, String>() {{
                put(false, "Email address format is incorrect.Must be of format : email@email.com");
            }};

        //all checks completed ,thus return true
        return new HashMap<Boolean, String>() {{
            put(true, null);
        }};

    }

    private boolean canUserInitiateMoneyTransfer(AccountTransactionEntity accountTransactionEntity) {
        AccountEntity senderAccount =
                accountRepository.getAccountById(accountTransactionEntity.getSendingAccountId());

        BigDecimal transactionAmount =
                accountTransactionEntity.getTransactionAmount();

        //if comparisonResult =-1,then it's less than value ,0=equal,1=greater
        int comparisonResult =
                senderAccount.getAccountBalance().compareTo(transactionAmount);
        if (comparisonResult >= 0)
            return true;
        return false;

    }

    void updateUserAccountBalancesAfterTransaction(AccountTransactionEntity accountTransactionEntity) throws Exception {
        AccountEntity senderAccount =
                accountRepository.getAccountById(accountTransactionEntity.getSendingAccountId());

        AccountEntity receiverAccount =
                accountRepository.getAccountById(accountTransactionEntity.getSendingAccountId());

        BigDecimal transactionAmount = accountTransactionEntity.getTransactionAmount();


        try {
            creditAccountInDatastore(receiverAccount, transactionAmount);

            debitAccountInDatastore(senderAccount, transactionAmount);

        } catch (Exception ex) {
            ex.printStackTrace();
            createAccountTransactionInDatastore(accountTransactionEntity, TransactionStatus.FAILED, "Error");
            throw new Exception("Failed Transaction");
        }
    }

    private void creditAccountInDatastore(AccountEntity accountEntity, BigDecimal amountToCredit) throws Exception {

        BigDecimal currentBalanceBeforeAddition = accountEntity.getAccountBalance();

        BigDecimal currentBalanceAfterAddition = currentBalanceBeforeAddition.add(amountToCredit);

        accountEntity.setAccountBalance(currentBalanceAfterAddition);

        accountRepository.updateUserAccountBalance(accountEntity, currentBalanceAfterAddition);
    }

    private void debitAccountInDatastore(AccountEntity accountEntity, BigDecimal amountToDebit) throws Exception {

        BigDecimal currentBalanceBeforeDebit = accountEntity.getAccountBalance();

        BigDecimal currentBalanceAfterDebit = currentBalanceBeforeDebit.subtract(amountToDebit);

        accountEntity.setAccountBalance(currentBalanceAfterDebit);

        accountRepository.updateUserAccountBalance(accountEntity, currentBalanceAfterDebit);
    }

    private void createAccountTransactionInDatastore(AccountTransactionEntity accountTransactionEntity, TransactionStatus transactionStatus, String reason) throws Exception {
        accountTransactionRepository.createAccountTransaction(accountTransactionEntity, transactionStatus, reason);
    }
}
