package com.example.accountservice.commands.dto;
public record DebitAccountDTO(
    String accountId,
    double amount,
    String currency
){}
