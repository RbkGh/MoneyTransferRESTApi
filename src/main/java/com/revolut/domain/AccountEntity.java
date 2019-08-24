package com.revolut.domain;

import java.math.BigDecimal;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:22
 */
public class AccountEntity {

    private long id;

    private String name;

    private String emailAddress;

    private BigDecimal accountBalance;

    public AccountEntity() {

    }

    public AccountEntity(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AccountEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public AccountEntity setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public AccountEntity setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }
}
