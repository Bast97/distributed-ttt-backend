package de.dttt;

public class WSBean {
    private String type;
    private String data;

    public WSBean(String type, String data) {
        // Constructor is never called, validation is done before updating match
        // if (data.matches("[ABC][123]")) {
            this.type = type;
            this.data = data;
        // }
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
