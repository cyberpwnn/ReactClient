package org.cyberpwn.react.network;

import org.cyberpwn.react.util.GMap;

public class RequestCallbackBasic implements Runnable {
    private GMap<String, String> data;
    private boolean ok;

    public void run(GMap<String, String> data, boolean ok) {
        this.data = data;
        this.ok = ok;
        run();
    }

    @Override
    public void run() {

    }

    public GMap<String, String> getData() {
        return data;
    }

    public boolean isOk() {
        return ok;
    }
}
