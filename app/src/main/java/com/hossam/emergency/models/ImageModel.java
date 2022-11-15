package com.hossam.emergency.models;

import java.io.Serializable;

public class ImageModel implements Serializable {

    private String url, id;
    private int position;
    private long timestamp;
    private boolean defalut;

    public ImageModel() {
    }

    public ImageModel(String url, String id, int position, long timestamp, boolean defalut) {
        this.url = url;
        this.id = id;
        this.position = position;
        this.timestamp = timestamp;
        this.defalut = defalut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDefalut() {
        return defalut;
    }

    public void setDefalut(boolean defalut) {
        this.defalut = defalut;
    }
}
