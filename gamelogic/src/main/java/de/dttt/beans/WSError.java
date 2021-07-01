package de.dttt.beans;

import com.google.gson.Gson;

public class WSError implements WSAbstractData {
    private static Gson gson = new Gson();

    public String toJson() {
        return gson.toJson(this);
    }
}
