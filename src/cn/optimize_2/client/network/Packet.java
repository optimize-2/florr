package cn.optimize_2.client.network;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.network.server.S00HandShakePacket;
import cn.optimize_2.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Base64;

public abstract class Packet {
    public static final int C00HandShakePacket = 100;
    public static final int C01KeepAlivePacket = 101;
    public static final int C02FlowerQuitPacket = 102;

    public static final int S00HandShakePacket = 200;
    public static final int S01KeepAlivePacket = 201;
    public static final int S02FlowerQuitPacket = 203;

    public void send(DatagramSocket ds, String IP, int UDP_Port) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeUTF(Base64.getEncoder().encodeToString(getPayload().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buf = baos.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, UDP_Port));
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void parse(String[] extracted) throws PacketParseException;

    public abstract String getPayload();
}
