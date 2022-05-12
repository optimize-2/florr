package cn.optimize_2.server;

import cn.optimize_2.client.network.Packet;
import cn.optimize_2.client.network.PacketParseException;
import cn.optimize_2.client.network.client.C00HandShakePacket;
import cn.optimize_2.client.network.server.S01KeepAlivePacket;
import cn.optimize_2.client.network.server.S02FlowerQuitPacket;
import cn.optimize_2.utils.log.LogTime;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.List;

public class Server extends Frame {

    public static int ID = 100;
    public static final int TCP_PORT = 55555;
    public static final int UDP_PORT = 55556;
    public static final int FLOWER_DIE_UDP_PORT = 55557;
    public List<Client> clients = new ArrayList<>();
    private Image offScreenImage = null;
    private static final int SERVER_HEIGHT = 500;
    private static final int SERVER_WIDTH = 300;

    public static Server ts;

    public HashMap<String, Client> clientHashMap = new HashMap<>();

    DatagramSocket ds = null;

    public static void main(String[] args) {
        ts = new Server();
        // ts.launchFrame();
        ts.start();
    }

    public void start() {
        new Thread(new UDPThread()).start();
        new Thread(new FlowerDieThread()).start();
        new Timer().schedule(new KeepAliveThread(), 0, 5000);
        // new Timer().schedule(new CheckKeepAliveThread(),0, 5000);
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(TCP_PORT);
            info("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                int UDP_PORT = dis.readInt();
                String flowerName = dis.readUTF();
                String flowerUUID = dis.readUTF();
                Client client = new Client(s.getInetAddress().getHostAddress(), UDP_PORT, ID);
                info("New connection from " + client.IP);
                clients.add(client);
                clientHashMap.put(flowerUUID, client);

                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(ID);
                dos.writeInt(Server.UDP_PORT);
                dos.writeInt(Server.FLOWER_DIE_UDP_PORT);
                ID++;
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
        }
    }

    public static void info(Object o) {
        System.out.println(LogTime.getTime() + " [Server Thread/INFO]: " + o);
    }

    public static void error(Object o) {
        System.out.println(LogTime.getTime() + " [Server Thread/ERROR]: " + o);
    }

    public static void debug(Object o) {
        if (cn.optimize_2.client.Client.DEVELOPMENT)
            System.out.println(LogTime.getTime() + " [Server Thread/DEBUG]: " + o);
    }

    byte[] buf = new byte[1024];

    private class KeepAliveThread extends TimerTask {
        @Override
        public void run() {
            Iterator<Client> itr = clients.iterator();
            ArrayList<Client> disconnected = new ArrayList<>();
            while (itr.hasNext()) {
                Client c = itr.next();
                send(new S01KeepAlivePacket(), c);
                if (new Date().getTime() - c.lastKeepAliveTime >= 10000) {
                    disconnected.add(c);
                    itr.remove();
                    info(c.IP + " disconnected: Time out");
                }
            }
            Iterator<Client> disconnectedItr = disconnected.iterator();
            while (disconnectedItr.hasNext()) {
                Client disconnectedClient = disconnectedItr.next();
                send(new S02FlowerQuitPacket(disconnectedClient.id), disconnectedClient);
                for (Client c : clients) {
                    send(new S02FlowerQuitPacket(disconnectedClient.id), c);
                }
                disconnectedItr.remove();
            }
        }
    }

    private class UDPThread implements Runnable {

        @Override
        public void run() {
            try {
                ds = new DatagramSocket(UDP_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }

            while (ds != null) {
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                try {
                    ds.receive(dp);
                    parse(dp);
                } catch (IOException | PacketParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void send(Packet packet, Client c) {
        packet.send(ds, c.IP, c.UDP_PORT);
    }

    private void parse(DatagramPacket dp) throws PacketParseException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
        DataInputStream dis = new DataInputStream(bais);
        int msgType = 0;
        String uuid = null;
        Client sender = null;
        String[] extracted = null;
        try {
            String payLoad = dis.readUTF();
            extracted = new String(Base64.getDecoder().decode(payLoad)).split(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            msgType = Integer.parseInt(extracted[0]);
            uuid = extracted[1];
            sender = clientHashMap.get(uuid);
            if (sender == null) {
                error("Invalid UUID: " + uuid);
                throw new PacketParseException("Exception thrown in parsing packet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        debug("Received a packet: " + msgType);
        switch (msgType) {
            case Packet.C00HandShakePacket:
                new C00HandShakePacket(sender).parse(extracted);
            case Packet.C01KeepAlivePacket:
                sender.lastKeepAliveTime = new Date().getTime();
        }
    }

    private class FlowerDieThread implements Runnable {
        byte[] buf = new byte[300];

        @Override
        public void run() {
            DatagramSocket ds = null;
            try {
                ds = new DatagramSocket(FLOWER_DIE_UDP_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            while (ds != null) {
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                ByteArrayInputStream bais = null;
                DataInputStream dis = null;
                try {
                    ds.receive(dp);
                    bais = new ByteArrayInputStream(buf, 0, dp.getLength());
                    dis = new DataInputStream(bais);
                    int deadFlowerPort = dis.readInt();
                    for (int i = 0; i < clients.size(); i++) {
                        Client client = clients.get(i);
                        if (client.UDP_PORT == deadFlowerPort) {
                            clients.remove(client);
                        }
                        info(client.IP + " disconnected");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (dis != null) {
                        try {
                            dis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (bais != null) {
                        try {
                            bais.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public class Client {
        String IP;
        public int UDP_PORT;
        public int id;
        public long lastKeepAliveTime = 0;

        public String UUID;

        public Client(String ipAddr, int UDP_PORT, int id) {
            this.IP = ipAddr;
            this.UDP_PORT = UDP_PORT;
            this.id = id;
            this.lastKeepAliveTime = new Date().getTime();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("Connections :", 30, 50);
        int y = 80;
        for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            g.drawString("id : " + c.id + " - IP : " + c.IP, 30, y);
            y += 30;
        }
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(SERVER_WIDTH, SERVER_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.white);
        gOffScreen.fillRect(0, 0, SERVER_WIDTH, SERVER_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void launchFrame() {
        this.setLocation(200, 100);
        this.setSize(SERVER_WIDTH, SERVER_HEIGHT);
        this.setTitle("Florr Server");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setBackground(Color.white);
        this.setVisible(true);
        new Thread(new PaintThread()).start();
    }

    class PaintThread implements Runnable {
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
