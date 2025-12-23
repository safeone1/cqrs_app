package com.example.accountservice.commands.aggregates;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.accountservice.commands.cmds.AddAccountCommand;
import com.example.accountservice.commands.cmds.DebitAccountCommand;
import com.example.accountservice.commands.enums.AccountStatus;
import com.example.accountservice.commands.events.AccountCreatedEvent;
import com.example.accountservice.commands.events.AccountCreditedEvent;
import com.example.accountservice.commands.events.AccountDebitedEvent;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        if (command.getInitialBalance() < 0) {
            throw new RuntimeException("Initial balance cannot be negative");
        }
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(AddAccountCommand command) {
        if (command.getAmount() < 0) {
            throw new RuntimeException("Amount to credit cannot be negative");
        }
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if (command.getAmount() < 0) {
            throw new RuntimeException("Amount to debit cannot be negative");
        }
        if (this.balance < command.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }
}
