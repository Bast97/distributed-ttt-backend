package de.dttt.beans;

import com.google.gson.Gson;

public class WSBean {
    private String type;
    private String data;

    private static Gson gson = new Gson();

    public WSBean(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public WSBean(String json) {
        this.type = gson.fromJson(json, WSBean.class).type;
        this.data = gson.fromJson(json, WSBean.class).data;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
