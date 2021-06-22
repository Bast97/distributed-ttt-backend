package de.dttt;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class TurnDecoder implements Decoder.Text<TicTacTurn> {

    private static Gson gson = new Gson();

    @Override
    public TicTacTurn decode(String s) throws DecodeException {
       // s = s.substring(1, s.length()-1).replace("\\\"", "\"");     //TODO: Removing Escape Characters as Workaround for testing, change later
        TicTacTurn message = gson.fromJson(s, TicTacTurn.class);
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
