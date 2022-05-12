package cn.optimize_2.client;

import cn.optimize_2.client.entity.Flower;
import cn.optimize_2.client.entity.card.Card;
import cn.optimize_2.client.entity.petal.Petal;
import cn.optimize_2.client.network.NetClient;
import cn.optimize_2.utils.log.LogTime;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static boolean DEVELOPMENT = true;

    private static Client c;

    private Flower myFlower;
    private NetClient nc = new NetClient(this);

    private List<Petal> petals = new ArrayList<>();
    private List<Petal> myPetals = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Flower> flowers = new ArrayList<>();

    private String UUID = java.util.UUID.randomUUID().toString();

    public String getUUID() {
        return UUID;
    }

    public static String NAME = "Florr Java Edition";
    public static String VERSION = "b1" + (DEVELOPMENT ? " | DEVELOPMENT BUILD" : "");
    public static Integer WIDTH = 780;
    public static Integer HEIGHT = 540;
    public static Boolean vSync = true;
    public static Florr game = new Florr();

    public static void main(String[] args) {
        game.init();
        Client.setInstance(new Client());

        game.gameLoop();
        game.dispose();
        Client.info("Game disposed");
        System.exit(0);
    }

    public static void info(Object o) {
        System.out.println(LogTime.getTime() + " [Client Thread/INFO]: " + o);
    }

    public static void error(Object o) {
        System.out.println(LogTime.getTime() + " [Client Thread/ERROR]: " + o);
    }

    public static void debug(Object o) {
        if (DEVELOPMENT)
            System.out.println(LogTime.getTime() + " [Client Thread/DEBUG]: " + o);
    }

    public void connect(String IP, String flowerName) {
        Client.info("Connecting to " + IP + "...");
        myFlower = new Flower(50 + (int) (Math.random() * (Florr.getGameWidth() -
                100)),
                50 + (int) (Math.random() * (Florr.getGameHeight() - 100)), flowerName, c);
        info("Flower initialized");
        nc.connect(IP);

        flowers.add(myFlower);
    }

    public List<Petal> getPetals() {
        return petals;
    }

    public void setPetals(List<Petal> petals) {
        this.petals = petals;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }

    public List<Card> getMyCards() {
        return myCards;
    }

    public void setMyCards(List<Card> myCards) {
        this.myCards = myCards;
    }

    public Flower getFlower() {
        return myFlower;
    }

    public Flower getFlowerById(Integer flowerId) {
        if (flowerId == getFlower().getId())
            return getFlower();
        for (Flower f : flowers) {
            if (f.getId() == flowerId)
                return f;
        }
        return null;
    }

    public Petal getPetalById(Integer petalId) {
        for (Petal f : petals) {
            if (f.getId() == petalId)
                return f;
        }
        return null;
    }

    public List<Petal> getMyPetals() {
        return myPetals;
    }

    public void setMyPetals(List<Petal> myPetals) {
        this.myPetals = myPetals;
    }

    public static Client getInstance() {
        return c;
    }

    public static void setInstance(Client c) {
        Client.c = c;
    }

    public void setMyFlower(Flower myFlower) {
        this.myFlower = myFlower;
    }

    public NetClient getNc() {
        return nc;
    }

    public void setNc(NetClient nc) {
        this.nc = nc;
    }
}