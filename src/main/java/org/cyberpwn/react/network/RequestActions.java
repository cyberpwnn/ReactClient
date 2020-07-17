package org.cyberpwn.react.network;

import org.cyberpwn.react.L;
import org.cyberpwn.react.util.GList;
import org.cyberpwn.react.util.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RequestActions extends Thread {
    private final RequestActionsCallback callback;
    private final NetworkedServer ns;
    private Socket s;

    public RequestActions(NetworkedServer ns, RequestActionsCallback callback) {
        this.callback = callback;
        this.ns = ns;
    }

    @Override
    public void run() {
        try {
            L.l("Polling action mapping...");
            s = new Socket(ns.getAddress(), ns.getPort());
            s.setSoTimeout(500);
            DataInputStream i = new DataInputStream(s.getInputStream());
            DataOutputStream o = new DataOutputStream(s.getOutputStream());
            PacketRequest pr = new PacketRequest(ns.getUsername(), ns.getPassword(), PacketRequestType.GET_ACTIONS.toString());
            L.n("OUT: " + pr.toString());
            o.writeUTF(pr.toString());
            o.flush();
            String response = i.readUTF();
            PacketResponse ps = new PacketResponse(new JSONObject(response));
            L.n("IN: " + ps.toString());
            GList<String> acts = new GList<String>();

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (ps.getString("type").equals("OK")) {
                for (Object j : ps.getJSON().getJSONArray("actions")) {
                    try {
                        acts.add((String) j);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                L.l("Discovered " + acts.size() + " actions.");
                L.l(acts.toString(", "));
                callback.run(acts, true);
                return;
            }

            callback.run(acts, false);
        } catch (Exception e) {

        }
    }
}
