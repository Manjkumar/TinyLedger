package org.example.ledger.controller;

import jakarta.validation.Valid;
import org.example.ledger.model.TransactionRequest;
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
    public ResponseEntity<Transaction> deposit(@RequestBody @Valid TransactionRequest request) {
        Transaction transaction = ledgerService.recordMovement(TransactionType.DEPOSIT, request.getAmount());
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody @Valid TransactionRequest request) {
        Transaction transaction = ledgerService.recordMovement(TransactionType.WITHDRAWAL, request.getAmount());
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
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
