package com.antwik.ixi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iota.ict.ixi.Ixi;
import org.iota.ict.ixi.IxiModule;
import org.iota.ict.ixi.context.IxiContext;
import org.iota.ict.model.transaction.Transaction;
import org.iota.ict.model.transaction.TransactionBuilder;

public class SpamIxi extends IxiModule {

    private static final Logger log = LogManager.getLogger("SpamIxi");
    private final SpamIxiContext context = new SpamIxiContext();

    private final Object waiter = new Object();

    public SpamIxi(Ixi ixi) {
        super(ixi);
    }

    @Override
    public IxiContext getContext() {
        return context;
    }

    public void run() {
        while (isRunning()) {
            if (context.getTransactionsPerMinute() > 0) {
                final TransactionBuilder builder = new TransactionBuilder();
                builder.asciiMessage("Spam message from Spam.ixi");
                builder.tag = "SPAM9IXI";
                final Transaction transaction = builder.buildWhileUpdatingTimestamp();
                ixi.submit(transaction);
                log.debug("Submitted spam transaction");
            }

            if (isRunning()) {
                synchronized (waiter) {
                    try {
                        if (context.getTransactionsPerMinute() > 0) {
                            waiter.wait((long) (1000 / (60f / context.getTransactionsPerMinute())));
                        } else {
                            waiter.wait(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
