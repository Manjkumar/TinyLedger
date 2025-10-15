package org.example.ledger.service;


import org.example.ledger.model.Transaction;
import org.example.ledger.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerServiceTest {

    private LedgerService ledgerService;

    @BeforeEach
    void setUp() {
        // Create a new instance before each test
        ledgerService = new LedgerService();
    }

    @Test
    void testInitialBalanceIsZero() {
        assertEquals("0.00", ledgerService.getCurrentBalance());
    }

    @Test
    void testDepositIncreasesBalance() {
        ledgerService.recordMovement(TransactionType.DEPOSIT, "100.50", "Initial funding");
        assertEquals("100.50", ledgerService.getCurrentBalance());
    }

    @Test
    void testWithdrawalDecreasesBalance() {
        // Setup initial balance
        ledgerService.recordMovement(TransactionType.DEPOSIT, "200.00", "Initial");

        // Perform withdrawal
        ledgerService.recordMovement(TransactionType.WITHDRAWAL, "50.25", "Rent payment");

        // Assert new balance
        assertEquals("149.75", ledgerService.getCurrentBalance());
    }

    @Test
    void testHistoryIsRecorded() {
        Transaction t1 = ledgerService.recordMovement(TransactionType.DEPOSIT, "10.00", "Small deposit");
        Transaction t2 = ledgerService.recordMovement(TransactionType.WITHDRAWAL, "5.00", "Small withdrawal");

        assertEquals(2, ledgerService.getTransactionHistory().size());

        // Verify running balance on transaction objects
        assertEquals("10.00", t1.getRunningBalance());
        assertEquals("5.00", t2.getRunningBalance());

        // Check the running balance in the history matches the final balance
        assertEquals("5.00", ledgerService.getCurrentBalance());
    }

    @Test
    void testWithdrawalWithInsufficientFundsThrowsException() {
        // Initial balance is 0.00
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ledgerService.recordMovement(TransactionType.WITHDRAWAL, "1.00", "Too much");
        });

        assertTrue(exception.getMessage().contains("Insufficient funds"));
        assertEquals("0.00", ledgerService.getCurrentBalance()); // Balance should be unchanged
    }

    @Test
    void testInvalidAmountFormatThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ledgerService.recordMovement(TransactionType.DEPOSIT, "one hundred", "Bad input");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ledgerService.recordMovement(TransactionType.DEPOSIT, "-10.00", "Negative input");
        });
    }
}
