package de.dttt;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class TurnDecoder implements Decoder.Text<WSBean> {

    private static Gson gson = new Gson();

    @Override
    public WSBean decode(String s) throws DecodeException {
        System.out.println(s);
       // s = s.substring(1, s.length()-1).replace("\\\"", "\"");     //TODO: Removing Escape Characters as Workaround for testing, change later
       WSBean message = gson.fromJson(s, WSBean.class);
       return message;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
