package com.malski.lazynium.utils;

import org.apache.log4j.Logger;

public abstract class Repeater<T> {
    protected final Logger log = Logger.getLogger(getClass());

    private int maxRetires = 5;

    public Repeater() {
    }

    public Repeater(int maxRetires) {
        this.maxRetires = maxRetires;
    }

    public T retry(int timeoutMilisec) throws Exception {
        int retries = 0;
        boolean conResult;
        T result;
        do {
            log.info("XXX Retry no: " + retries);
            result = method();
            if (retries > 0) {
                wait(timeoutMilisec);
            }
            conResult = conditionCheck(result);
            retries++;
        } while (retries < maxRetires && conResult);

        return result;
    }

    public abstract T method() throws Exception;

    public abstract boolean conditionCheck(T result);

    public void wait(int milisec) {
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}