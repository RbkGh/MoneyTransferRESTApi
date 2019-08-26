package com.revolut.domain;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:22
 */
@Entity(name = "account ")
@Table(name = "account ")
public class AccountEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email_address",unique = true)
    private String emailAddress;

    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    public AccountEntity() {

    }

    public AccountEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
