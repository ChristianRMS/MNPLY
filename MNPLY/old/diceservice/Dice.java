package diceservice;

import static spark.Spark.get;
import static spark.Spark.port;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import services.Service;
import util.Constants;

public class Dice {

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		Gson gson = new Gson();

		// port(4577);
		//
		// SSLContext sslcontext = SSLContexts.custom()
		// .loadTrustMaterial(null, new TrustSelfSignedStrategy())
		// .build();
		//
		// SSLConnectionSocketFactory sslsf = new
		// SSLConnectionSocketFactory(sslcontext);
		// CloseableHttpClient httpclient = HttpClients.custom()
		// .setSSLSocketFactory(sslsf)
		// .build();
		// Unirest.setHttpClient(httpclient);
		//
		// Gives a single dice roll
		get("/dice", (req, res) -> {
			res.status(200);
			res.header("Content-Type", "application/json");
			Gson gsonB = new GsonBuilder().create();
			return gsonB.toJson(new DiceController(), DiceController.class);
		});

		try {
			Unirest.post("http://172.18.0.5:4567/services").header("Content-Type", "application/json")
					.queryString("name", "DERDICEISTHEISS").queryString("description", "CI Gives you a dice roll")
					.queryString("service", "dice").queryString("uri", Constants.DICESERVICE + "/")
					.body(new Gson().toJson(new Service("DICE", "CI Gives you a dice roll", "dice",
							Constants.DICESERVICE)))
					.asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	// .header("accept", "application/json") (?)
}