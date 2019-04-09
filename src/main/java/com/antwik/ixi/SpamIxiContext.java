package com.antwik.ixi;

import org.iota.ict.ixi.context.ConfigurableIxiContext;
import org.json.JSONObject;

public class SpamIxiContext extends ConfigurableIxiContext {

    private static final String TRANSACTIONS_PER_MINUTE = "transactionsPerMinute";
    private static final int TRANSACTIONS_PER_MINUTE_DEFAULT = 1;
    private int transactionsPerMinute = TRANSACTIONS_PER_MINUTE_DEFAULT;

    SpamIxiContext() {
        super(new JSONObject().put(TRANSACTIONS_PER_MINUTE, TRANSACTIONS_PER_MINUTE_DEFAULT));
    }

    @Override
    protected void validateConfiguration(JSONObject newConfiguration) {
        if(!newConfiguration.has(TRANSACTIONS_PER_MINUTE)) {
            throw new IllegalArgumentException(String.format("field '%s' is missing", TRANSACTIONS_PER_MINUTE));
        }
        if(newConfiguration.getInt(TRANSACTIONS_PER_MINUTE) < 0) {
            throw new IllegalArgumentException(String.format("field '%s' cannot be negative", TRANSACTIONS_PER_MINUTE));
        }
    }

    @Override
    protected void applyConfiguration() {
        transactionsPerMinute = configuration.getInt(TRANSACTIONS_PER_MINUTE);
    }

    public int getTransactionsPerMinute() {
        return transactionsPerMinute;
    }
}
