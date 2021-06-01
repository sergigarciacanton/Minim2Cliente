package com.example.minim2sergigarciaqp2021.server;

public class Badge {
    String name;
    String URL;

    public Badge() { }

    public Badge(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
