package com.revolut.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:22
 */
@Entity
public class AccountEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="emailAddress")
    private String emailAddress;

    @Column(name="accountBalance")
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
