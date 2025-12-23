package com.example.accountservice.commands.dto;
public record CreditAccountDTO(
        String accountId,
        double amount,
        String currency
) {
}
