package org.example.ledger.model;

import java.time.LocalDateTime;

public class Transaction {
    private final String id;
    private final LocalDateTime timestamp;
    private final TransactionType type;
    private final String amount;
    private final String runningBalance;

    public Transaction(String id, TransactionType type, String amount, String runningBalance) {
        this.id = id;
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.runningBalance = runningBalance;
    }

    public String getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public TransactionType getType() { return type; }
    public String getAmount() { return amount; }
    public String getRunningBalance() { return runningBalance; }
}
