package de.dttt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class MatchmakerInfo {

	private String player1Id, player2Id;
	private boolean matchIdIsValid;

	public MatchmakerInfo(String gid) {
		URL url;
		Gson gson = new Gson();
		try {
			String baseurl = System.getenv("MATCHMAKER_HOST");
			String port = System.getenv("MATCHMAKER_PORT");
			url = new URL("http://" + baseurl + ":" + port + "/matchinfo/" + gid);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int status = con.getResponseCode();
			if (status == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
				con.disconnect();

				MatchmakerInfo generated = gson.fromJson(content.toString(), MatchmakerInfo.class);
				this.matchIdIsValid = generated.isValid();
				this.player1Id = generated.getX();
				this.player2Id = generated.getO();
			}
			else{
				System.out.println("Matchmaker not responding correctly!");
			}
		} catch (IOException e) {
			System.out.println("Error establishing connection with matchmaker!");
			e.printStackTrace();
		}
	}

	public String getX() {
		return player1Id;
	}

	public void setX(String x) {
		this.player1Id = x;
	}

	public String getO() {
		return player2Id;
	}

	public void setO(String o) {
		this.player2Id = o;
	}

	public boolean isValid() {
		return matchIdIsValid;
	}

	public void setValid(boolean isValid) {
		this.matchIdIsValid = isValid;
	}

}
