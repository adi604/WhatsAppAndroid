package com.example.whatapp.entities;

public class Invitation {
    private String from;
    private String to;
    private String server;

    public Invitation(String f, String t, String s) {
        from = f;
        to = t;
        server = s;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
