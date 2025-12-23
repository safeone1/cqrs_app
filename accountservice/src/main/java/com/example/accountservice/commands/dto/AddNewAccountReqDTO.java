package com.example.accountservice.commands.dto;

public record AddNewAccountReqDTO(
        double initBalance,
        String currency
) {}
