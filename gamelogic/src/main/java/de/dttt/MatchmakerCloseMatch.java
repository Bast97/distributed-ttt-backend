package de.dttt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MatchmakerCloseMatch {

	public static void closeMatch(String gid) {
		URL url;
		try {
			String baseurl = System.getenv("MATCHMAKER_HOST");
			String port = System.getenv("MATCHMAKER_PORT");
			url = new URL("http://" + baseurl + ":" + port + "/closeMatch/" + gid);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
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
				System.out.println("sent closeMatch");

			}
			else{
				System.out.println("Matchmaker not responding correctly!");
				System.out.println("HTTP status: " + Integer.toString(status));
			}
		} catch (IOException e) {
			System.out.println("Error establishing connection with matchmaker!");
			e.printStackTrace();
		}
	}

}
