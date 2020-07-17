package org.cyberpwn.react.network;

import org.cyberpwn.react.util.GMap;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Request extends Thread {
    private final RequestCallback callback;
    private final NetworkedServer ns;

    public Request(NetworkedServer ns, RequestCallback callback) {
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
            PacketRequest pr = new PacketRequest(ns.getUsername(), ns.getPassword(), PacketRequestType.GET_SAMPLES.toString());
            o.writeUTF(pr.toString());
            o.flush();
            String response = i.readUTF();
            PacketResponse ps = new PacketResponse(new JSONObject(response));
            GMap<String, Double> data = new GMap<>();
            String console = "";

            if (ps.getString("type").equals("OK")) {
                for (String j : ps.getJSON().keySet()) {
                    if (j.equals("type")) {
                        continue;
                    }

                    if (j.equals("console-s")) {
                        console = ps.getJSON().getString(j).replace("[0;36;1m", "").replace("[0;30;22m", "").replace("[0;34;22m", "").replace("[0;32;22m", "").replace("[0;36;22m", "").replace("[0;31;22m", "").replace("[0;35;22m", "").replace("[0;37;22m", "").replace("[0;34;1m", "").replace("[5m", "").replace("[21m", "").replace("[9m", "").replace("[4m", "").replace("[3m", "").replace("[0;33;22m", "").replace("[0;31;1m", "").replace("[0;35;1m", "").replace("[0;32;1m", "").replace("[0;33;22m", "").replace("[0;33;1m", "").replace("[0;37;1m", "").replace("[0;30;1m", "").replace("[0;30;1m", "").replace("[m", "").replace("", "").replace("[m", "");

                        continue;
                    }

                    try {
                        data.put(j, ps.getJSON().getDouble(j));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                callback.run(data, console, true);
            }

            callback.run(data, console, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
