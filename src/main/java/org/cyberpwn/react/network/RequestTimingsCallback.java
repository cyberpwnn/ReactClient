package org.cyberpwn.react.network;

public class RequestTimingsCallback implements Runnable {
    private String timings;
    private boolean ok;

    public void run(String timings, boolean ok) {
        this.timings = timings;
        this.ok = ok;
        run();
    }

    @Override
    public void run() {

    }

    public String getTimings() {
        return timings;
    }

    public boolean isOk() {
        return ok;
    }
}
