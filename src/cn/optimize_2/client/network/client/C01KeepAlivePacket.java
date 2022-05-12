package cn.optimize_2.client.network.client;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.network.Packet;
import cn.optimize_2.client.network.PacketParseException;

public class C01KeepAlivePacket extends Packet {
    private int msgType = Packet.C01KeepAlivePacket;
    private Client c;

    public C01KeepAlivePacket() {
    }

    public C01KeepAlivePacket(Client c) {
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
        } catch (Exception e) {
            throw new PacketParseException("Exception thrown in parsing packet");
        }
    }
}
