package org.cyberpwn.react.network;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RequestTimings extends Thread {
    private final RequestTimingsCallback callback;
    private final NetworkedServer ns;

    public RequestTimings(NetworkedServer ns, RequestTimingsCallback callback) {
        this.callback = callback;
        this.ns = ns;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket(ns.getAddress(), ns.getPort());
            s.setSoTimeout(500);
            DataInputStream i = new DataInputStream(s.getInputStream());
            DataOutputStream o = new DataOutputStream(s.getOutputStream());
            PacketRequest pr = new PacketRequest(ns.getUsername(), ns.getPassword(), PacketRequestType.GET_TIMINGS.toString());
            o.writeUTF(pr.toString());
            o.flush();
            String response = i.readUTF();
            PacketResponse ps = new PacketResponse(new JSONObject(response));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (ps.getString("type").equals("OK")) {
                String timings = ps.getString("timings");
                callback.run(timings, false);
            }
        } catch (Exception ignored) {

        }
    }
}
