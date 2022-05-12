package cn.optimize_2.client.network;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.network.client.*;
import cn.optimize_2.client.network.server.*;
import cn.optimize_2.server.Server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;

public class NetClient {
    private Client c;
    private int UDP_PORT;
    private String serverIP;
    private int serverUDPPort;
    private int FLOWER_DEAD_UDP_PORT;
    private DatagramSocket ds = null;

    public void setUDP_PORT(int UDP_PORT) {
        this.UDP_PORT = UDP_PORT;
    }

    public NetClient(Client c) {
        this.c = c;
        try {
            this.UDP_PORT = getRandomUDPPort();
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public void connect(String ip) {
        serverIP = ip;
        Socket s = null;
        try {
            ds = new DatagramSocket(UDP_PORT);
            try {
                s = new Socket(ip, Server.TCP_PORT);
            } catch (Exception e1) {
                ds.close();
            }
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(UDP_PORT);
            dos.writeUTF(Client.getInstance().getFlower().getName());
            dos.writeUTF(Client.getInstance().getUUID());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int id = dis.readInt();
            this.serverUDPPort = dis.readInt();
            this.FLOWER_DEAD_UDP_PORT = dis.readInt();
            // this.uuid = dis.read();
            c.getFlower().setId(id);
            Client.info("Connected to " + ip + " successfully...");
            Client.info("Client UDP Port: " + UDP_PORT);
            Client.info("UUID: " + Client.getInstance().getUUID());
            Client.info("Flower name: " + Client.getInstance().getFlower().getName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null)
                    s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new Thread(new UDPThread()).start();
        new Timer().schedule(new KeepAliveThread(), 0, 5000);
    }

    private int getRandomUDPPort() {
        return 55558 + (int) (Math.random() * 9000);
    }

    public void send(Packet packet) {
        packet.send(ds, serverIP, serverUDPPort);
    }

    private class KeepAliveThread extends TimerTask {
        @Override
        public void run() {
            send(new C01KeepAlivePacket());
        }
    }

    public class UDPThread implements Runnable {

        byte[] buf = new byte[1024];

        @Override
        public void run() {
            send(new C00HandShakePacket(4, 5, 9));
            while (null != ds) {
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                try {
                    ds.receive(dp);
                    parse(dp);
                } catch (IOException | PacketParseException e) {
                    e.printStackTrace();
                }
            }
        }

        private void parse(DatagramPacket dp) throws PacketParseException {
            ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
            DataInputStream dis = new DataInputStream(bais);
            int msgType = 0;
            String[] extracted = null;
            try {
                String payLoad = dis.readUTF();
                extracted = new String(Base64.getDecoder().decode(payLoad)).split(" ");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                msgType = Integer.parseInt(extracted[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Client.debug("Received packet: " + msgType);
            switch (msgType) {
                case Packet.S00HandShakePacket:
                    new S00HandShakePacket().parse(extracted);
            }
        }
    }

    public void sendClientDisconnectMsg() {
        if (serverIP == null)
            return;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(88);
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (null != baos) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] buf = baos.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length,
                    new InetSocketAddress(serverIP, FLOWER_DEAD_UDP_PORT));
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
