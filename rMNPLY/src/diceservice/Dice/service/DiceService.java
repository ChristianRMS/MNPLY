package diceservice.Dice.service;

import static spark.Spark.get;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import bankservice.Banks.util.ConstantsBank;
import diceservice.Dice.util.IpFinder;
import diceservice.Dice.controller.DiceController;
import util.ServiceTemplateBank;

/**
 * created by Christian Zen christian.zen@outlook.de Date of creation:
 * 26.04.2016
 */
public class DiceService {

	public static int port = 4567;
	public static String ip = IpFinder.getIP();
	public static String URL = "http://" + ip + ":" + port;
	public static String URLService = URL + "/dice";

	/*
	 * remove old stuff
	 */
	public static void removeOldStuff() {
//		for (int i = 0; i <= 999; i++) {
//			org.json.JSONObject jObj = new org.json.JSONObject(
//					String url = "http://141.22.34.15/cnt/172.18.0.5/4567/services/" + i;
//					Unirest.get("http://141.22.34.15/cnt/172.18.0.5/4567/services/" + i).getBody());
//			if (!jObj.isNull("describtion")) {
//				if (jObj.getString("describtion").equals("CI Gives you a dice roll")) {
//					Unirest.delete("http://141.22.34.15/cnt/172.18.0.5/4567" + jObj.getString("_uri"));
//					System.out.println("http://141.22.34.15/cnt/172.18.0.5/4567" + jObj.getString("_uri"));
//				}
//			}
	}

	public static void main(String[] args) {

		Gson gson = new Gson();

		// find and remove old Dice Services

		// Gives a single dice roll
		get("/dice", (req, res) -> {
			res.status(200);
			res.header("Content-Type", "application/json");
			Gson gsonB = new GsonBuilder().create();
			return gsonB.toJson(new DiceController(), DiceController.class);
		});

		try {
			Unirest.post("http://172.18.0.5:4567/services").header("Content-Type", "application/json")
					.queryString("name", "DICE").queryString("description", "CI Gives you a dice roll")
					.queryString("service", "dice").queryString("uri", URLService)
					.body(new Gson()
							.toJson(new ServiceTemplateBank("DICE", "CI Gives you a dice roll", "dice", URLService)))
					.asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		//removeOldStuff();

	}
}
