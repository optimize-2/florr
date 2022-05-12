package cn.optimize_2.client.network.server;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.network.Packet;
import cn.optimize_2.client.network.PacketParseException;

public class S01KeepAlivePacket extends Packet {
    private int msgType = Packet.S01KeepAlivePacket;
    private Client c;

    public S01KeepAlivePacket() {
    }

    public S01KeepAlivePacket(Client c) {
        this.c = c;
    }

    @Override
    public String getPayload() {
        return msgType + "";
    }

    @Override
    public void parse(String[] extracted) throws PacketParseException {
        try {
            String msgType = extracted[0];
        } catch (Exception e) {
            throw new PacketParseException("Exception thrown in parsing packet");
        }
    }
}
