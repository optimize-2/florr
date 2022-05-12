package cn.optimize_2.client.network.client;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.network.Packet;
import cn.optimize_2.client.network.PacketParseException;
import cn.optimize_2.client.network.server.S00HandShakePacket;
import cn.optimize_2.server.Server;

public class C00HandShakePacket extends Packet {
    private int msgType = Packet.C00HandShakePacket;
    private Server.Client c;
    private Integer x, y, z;

    public C00HandShakePacket() {
    }

    public C00HandShakePacket(Server.Client c) {
        this.c = c;
    }

    public C00HandShakePacket(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String getPayload() {
        return msgType + " " + Client.getInstance().getUUID() + " " + x + " " + y + " " + z;
    }

    @Override
    public void parse(String[] extracted) throws PacketParseException {
        try {
            String msgType = extracted[0];
            String UUID = extracted[1];
            int x = Integer.parseInt(extracted[2]);
            int y = Integer.parseInt(extracted[3]);
            int z = Integer.parseInt(extracted[4]);
            if (x + y == z) {
                Server.ts.send(new S00HandShakePacket(4, 5, 9), c);
            }
        } catch (Exception e) {
            throw new PacketParseException("Exception thrown in parsing packet");
        }
    }
}
