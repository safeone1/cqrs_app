package com.example.accountservice.commands.controllers;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.accountservice.commands.cmds.AddAccountCommand;
import com.example.accountservice.commands.cmds.CreditAccountCommand;
import com.example.accountservice.commands.cmds.DebitAccountCommand;
import com.example.accountservice.commands.dto.AddNewAccountReqDTO;
import com.example.accountservice.commands.dto.CreditAccountDTO;
import com.example.accountservice.commands.dto.DebitAccountDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/addAccount")
    public CompletableFuture<String> createAccount(@RequestBody AddNewAccountReqDTO request){
        CompletableFuture<String> result = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initBalance(),
                request.amount(),
                request.currency()));
        return result;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO request){
        CompletableFuture<String> result = commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()));
        return result;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDTO request) {
        CompletableFuture<String> result = commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()));
        return result;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}