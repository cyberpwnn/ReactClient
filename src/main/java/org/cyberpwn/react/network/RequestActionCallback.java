package org.cyberpwn.react.network;

public class RequestActionCallback implements Runnable {
    private boolean ok;

    public void run(boolean ok) {
        this.ok = ok;
        run();
    }

    @Override
    public void run() {

    }

    public boolean isOk() {
        return ok;
    }
}
