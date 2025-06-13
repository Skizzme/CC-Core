package me.skizzme.cc.listeners;

import me.skizzme.cc.util.FileUtils;
import me.skizzme.cc.util.TimeUtils;
import net.impactdev.impactor.api.economy.events.EconomyTransactionEvent;
import net.impactdev.impactor.api.economy.transactions.details.EconomyTransactionType;
import net.kyori.event.EventSubscriber;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class TransactionListener implements EventSubscriber<EconomyTransactionEvent.Pre> {

    @Override
    public void on(EconomyTransactionEvent.@NonNull Pre event) throws Throwable {
        if (event.amount().equals(BigDecimal.ZERO)) {
            return;
        }
        String log = "Amount: " + event.amount() + ", Type: " + event.type().name() + ", User ID: " + event.account().owner()  + ", Balance: " + event.account().balance();
        FileUtils.appendFile("economy_log.txt", "[" + TimeUtils.getTimeDefault() + "] " + log + "\n");
    }

    @Override
    public int postOrder() {
        return EventSubscriber.super.postOrder();
    }

    @Override
    public boolean acceptsCancelled() {
        return EventSubscriber.super.acceptsCancelled();
    }
}
