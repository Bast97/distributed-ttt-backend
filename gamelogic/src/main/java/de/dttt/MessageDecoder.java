package de.dttt;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import de.dttt.beans.WSBean;

public class MessageDecoder implements Decoder.Text<WSBean> {

	private static Gson gson = new Gson();

	@Override
	public WSBean decode(String s) throws DecodeException {
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
