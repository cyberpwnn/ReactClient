package org.cyberpwn.react.network;

import org.cyberpwn.react.util.GList;

public class NetworkScheduler extends Thread {
    private final GList<NetworkedServer> ns;
    private final long ms;

    public NetworkScheduler(GList<NetworkedServer> ns, long ms) {
        this.ns = ns;
        this.ms = ms;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            for (NetworkedServer i : ns) {
                i.requestActions();
                i.requestSample();
                i.requestTimings();
            }

            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
