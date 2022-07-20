package com.ryfsystems.smi.Models;

public class Server {
    private int serverId;
    private String serverName;
    private String serverAddress;
    private int serverImage;

    public Server(int serverId, String serverName, String serverAddress, int serverImage) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.serverAddress = serverAddress;
        this.serverImage = serverImage;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerImage() {
        return serverImage;
    }

    public void setServerImage(int serverImage) {
        this.serverImage = serverImage;
    }
}
