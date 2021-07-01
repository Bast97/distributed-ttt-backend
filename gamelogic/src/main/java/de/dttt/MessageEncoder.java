package de.dttt;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import de.dttt.beans.WSBean;

public class MessageEncoder implements Encoder.Text<WSBean> {

	private static Gson gson = new Gson();

	@Override
	public String encode(WSBean message) throws EncodeException {
		String json = gson.toJson(message);
		return json;
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
