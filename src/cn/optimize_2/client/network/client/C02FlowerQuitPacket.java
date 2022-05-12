package cn.optimize_2.client.network.client;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.network.Packet;
import cn.optimize_2.client.network.PacketParseException;
import cn.optimize_2.client.network.server.S02FlowerQuitPacket;
import cn.optimize_2.server.Server;

public class C02FlowerQuitPacket extends Packet {
    private int msgType = Packet.C02FlowerQuitPacket;
    private Server.Client c;

    public C02FlowerQuitPacket() {
    }

    public C02FlowerQuitPacket(Server.Client c) {
        this.c = c;
    }

    @Override
    public String getPayload() {
        return msgType + " " + Client.getInstance().getUUID();
    }

    @Override
    public void parse(String[] extracted) throws PacketParseException {
        try {
            String msgType = extracted[0];
            String UUID = extracted[1];
            int flowerId = Server.ts.clientHashMap.get(UUID).id;
            for (Server.Client c : Server.ts.clients) {
                Server.ts.send(new S02FlowerQuitPacket(flowerId), c);
            }
        } catch (Exception e) {
            throw new PacketParseException("Exception thrown in parsing packet");
        }
    }
}
