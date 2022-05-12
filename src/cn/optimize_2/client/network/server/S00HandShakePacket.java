package cn.optimize_2.client.network.server;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.network.Packet;
import cn.optimize_2.client.network.PacketParseException;

public class S00HandShakePacket extends Packet {
    private int msgType = Packet.S00HandShakePacket;
    private Client c;
    private Integer x, y, z;

    public S00HandShakePacket() {
    }

    public S00HandShakePacket(Client c) {
        this.c = c;
    }

    public S00HandShakePacket(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String getPayload() {
        return msgType + " " + x + " " + y + " " + z;
    }

    @Override
    public void parse(String[] extracted) throws PacketParseException {
        try {
            String msgType = extracted[0];
            int x = Integer.parseInt(extracted[1]);
            int y = Integer.parseInt(extracted[2]);
            int z = Integer.parseInt(extracted[3]);
            if (x + y == z) {

            }
        } catch (Exception e) {
            throw new PacketParseException("Exception thrown in parsing packet");
        }
    }
}
