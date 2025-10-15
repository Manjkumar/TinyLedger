package org.example.ledger.model;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public class TransactionRequest {

    // Ensures the amount string is not null and not just whitespace
    @NotBlank(message = "Amount is required and cannot be empty.")
    private String amount;

    @Size(min = 3, max = 100, message = "Description must be between 3 and 100 characters.")
    private String description;

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
