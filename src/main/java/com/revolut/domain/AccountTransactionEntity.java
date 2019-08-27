package com.revolut.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * author: acerbk
 * Date: 2019-08-26
 * Time: 20:50
 */
@Entity(name = "account_transaction ")
@Table(name = "account_transaction ")
public class AccountTransactionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Sender Account id must be present")
    @Column(nullable = false)
    private Long sendingAccountId;

    @NotNull(message = "Receiving Account id must be present")
    @Column(nullable = false)
    private Long receivingAccountId;

    @NotNull(message = "Transaction Account cannot be absent.")
    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column
    private Date dateOfTransaction;
    /**
     * reason for transaction failure if transaction failed.
     */
    @Column
    private String reason;

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public AccountTransactionEntity setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AccountTransactionEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getSendingAccountId() {
        return sendingAccountId;
    }

    public AccountTransactionEntity setSendingAccountId(Long sendingAccountId) {
        this.sendingAccountId = sendingAccountId;
        return this;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public AccountTransactionEntity setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public Long getReceivingAccountId() {
        return receivingAccountId;
    }

    public AccountTransactionEntity setReceivingAccountId(Long receivingAccountId) {
        this.receivingAccountId = receivingAccountId;
        return this;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public AccountTransactionEntity setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public AccountTransactionEntity setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public String toString() {
        return "AccountTransactionEntity{" +
                "id=" + id +
                ", sendingAccountId=" + sendingAccountId +
                ", receivingAccountId=" + receivingAccountId +
                ", transactionStatus=" + transactionStatus +
                ", reason='" + reason + '\'' +
                '}';
    }
}
