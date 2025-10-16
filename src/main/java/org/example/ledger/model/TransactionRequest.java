package org.example.ledger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

public class TransactionRequest {
    @NotBlank(message = "Amount is required and cannot be empty.")
    private String amount;

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
