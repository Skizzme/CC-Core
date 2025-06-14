package me.skizzme.cc.listeners;

import me.skizzme.cc.CCCore;
import me.skizzme.cc.util.FileUtils;
import me.skizzme.cc.util.StringUtils;
import net.impactdev.impactor.api.economy.events.EconomyTransactionEvent;
import net.kyori.event.EventSubscriber;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.math.BigDecimal;

public class TransactionListener implements EventSubscriber<EconomyTransactionEvent.Pre> {

    @Override
    public void on(EconomyTransactionEvent.@NonNull Pre event) throws Throwable {
        if (event.amount().equals(BigDecimal.ZERO)) {
            return;
        }
        String log = "Amount: " + event.amount() +
                ", Type: " + event.type().name() +
                ", User ID: " + event.account().owner() +
                ", Username: " + CCCore.getServer().getPlayerManager().getPlayer(event.account().owner()) +
                ", Balance: " + event.account().balance();

        FileUtils.appendFile("economy_log.txt", "[" + StringUtils.getTimeDefault() + "] " + log + "\n");
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
