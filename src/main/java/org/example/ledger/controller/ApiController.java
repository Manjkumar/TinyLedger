package org.example.ledger.controller;

import org.example.ledger.model.Transaction;
import org.example.ledger.model.TransactionType;
import org.example.ledger.service.LedgerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ledger")
public class ApiController {

    private final LedgerService ledgerService;

    public ApiController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, String> request) {
        String amount = request.get("amount");
        String description = request.getOrDefault("description", "Deposit");
        try {
            Transaction transaction = ledgerService.recordMovement(TransactionType.DEPOSIT, amount, description);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody Map<String, String> request) {
        String amount = request.get("amount");
        String description = request.getOrDefault("description", "Withdrawal");
        try {
            Transaction transaction = ledgerService.recordMovement(TransactionType.WITHDRAWAL, amount, description);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, String>> getBalance() {
        String balance = ledgerService.getCurrentBalance();
        return ResponseEntity.ok(Map.of("currentBalance", balance));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getHistory() {
        return ResponseEntity.ok(ledgerService.getTransactionHistory());
    }
}
