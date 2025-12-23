
package com.example.accountservice.commands.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.accountservice.commands.cmds.AddAccountCommand;
import com.example.accountservice.commands.dto.AddNewAccountReqDTO;


@RestController
@RequestMapping("/commands")
public class AccountCommandController {




    private CommandGateway commandGateway;


    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/addAccount")
    public CompletableFuture<String> AddAccount(@RequestBody AddNewAccountReqDTO req) {

        CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(
            UUID.randomUUID().toString(),
                    req.initBalance(),
                    req.currency()
        ));

        return response;


    }
}