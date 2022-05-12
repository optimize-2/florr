package cn.optimize_2.client.network.server;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.entity.Flower;
import cn.optimize_2.client.network.NetClient;
import cn.optimize_2.client.network.Packet;
import cn.optimize_2.client.network.PacketParseException;
import cn.optimize_2.server.Server;

public class S02FlowerQuitPacket extends Packet {
    private int msgType = Packet.S02FlowerQuitPacket;
    private int id;
    private Server.Client c;

    public S02FlowerQuitPacket() {
    }

    public S02FlowerQuitPacket(int id) {
        this.id = id;
    }

    public S02FlowerQuitPacket(Server.Client c) {
        this.c = c;
    }

    @Override
    public String getPayload() {
        return msgType + " " + id;
    }

    @Override
    public void parse(String[] extracted) throws PacketParseException {
        try {
            // String msgType = extracted[0];
            int flowerId = Integer.parseInt(extracted[1]);
            if (flowerId == Client.getInstance().getFlower().getId()) {
                Client.getInstance().game.state.change("disconnect");
            } else {
                Flower f = Client.getInstance().getFlowerById(flowerId);
                Client.info(f.getName() + " Left");
                Client.getInstance().getFlowers().remove(f);
            }
        } catch (Exception e) {
            throw new PacketParseException("Exception thrown in parsing packet");
        }
    }
}
