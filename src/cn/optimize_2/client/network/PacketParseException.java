package cn.optimize_2.client.network;

public class PacketParseException extends Exception {
    private String message;
    public PacketParseException(String message) {
        super(message);
        this.message = message;
    }
}
