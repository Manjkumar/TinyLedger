package org.example.ledger.service;

import org.example.ledger.model.Transaction;
import org.example.ledger.model.TransactionType;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LedgerService {

    // In-memory data structures
    private final List<Transaction> transactionHistory = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong nextId = new AtomicLong(1);
    private BigDecimal currentBalance = BigDecimal.ZERO;

    // Configuration for monetary values
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * Records a new money movement (deposit or withdrawal).
     * @param type The type of transaction.
     * @param amountStr The amount as a String (for precision).
     * @param description A brief description.
     * @return The newly created Transaction.
     * @throws IllegalArgumentException if the amount is invalid or insufficient funds for withdrawal.
     */
    public synchronized Transaction recordMovement(TransactionType type, String amountStr, String description) {
        BigDecimal amount;
        try {
            amount = new BigDecimal(amountStr)
                    .setScale(SCALE, ROUNDING_MODE);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format.");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid amount format.");
        }

        BigDecimal newBalance = getBalance(type, amount);
        Transaction newTransaction = new Transaction(
                String.valueOf(nextId.getAndIncrement()),
                type,
                amount.toPlainString(),
                description,
                newBalance.setScale(SCALE, ROUNDING_MODE).toPlainString()
        );

        // Update state
        transactionHistory.add(newTransaction);
        currentBalance = newBalance;
        return newTransaction;
    }

    private BigDecimal getBalance(TransactionType type, BigDecimal amount) {
        BigDecimal newBalance;
        if (type == TransactionType.DEPOSIT) {
            newBalance = currentBalance.add(amount);
        } else if (type == TransactionType.WITHDRAWAL) {
            if (currentBalance.compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient funds for withdrawal.");
            }
            newBalance = currentBalance.subtract(amount);
        } else {
            throw new IllegalArgumentException("Unknown transaction type.");
        }
        return newBalance;
    }

    /**
     * @return The current balance as a formatted String.
     */
    public String getCurrentBalance() {
        return currentBalance.setScale(SCALE, ROUNDING_MODE).toPlainString();
    }

    /**
     * @return An unmodifiable list of all transactions.
     */
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }
}
