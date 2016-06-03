package clientservice.Client.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

import com.mashape.unirest.http.Unirest;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;

import clientservice.Client.util.Service;

public class ClientController {

	static String yelloPageAdress2 = "http://141.22.34.15/cnt/172.18.0.5/4567/services/of/name/";

	static String yelloPageAdress = "http://172.18.0.5:4567/services/of/name/";

	// client functions:

	// erstelle ein Spiel:
	public void createGame(String gameName) {
		System.out.println("fu2");
		// get gameservice uri from yellowpage
		String gameServiceUri = "";
		try {
			// get Services
			List<Service> groupServices = getServicesByGroupName("group_42");
			for (Service service : groupServices) {

				System.out.println("gameService found: " + service.getUri());

				if (service.getService().equals("games"))
					gameServiceUri = service.getUri();

			}
		} catch (UnirestException ex) {
			ex.printStackTrace();
		}

		// post auf game -> spiel erstellen param: gameName
		System.out.println(gameServiceUri);

		if (gameServiceUri != null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.append("name", gameName);
			HttpResponse<String> response = Unirest.post(gameServiceUri + "/")
					.header("Content-Type", "application/json").body(jsonObject);
			// System.out.println(response);
		}

	}

	public static List<Service> getServicesByGroupName(String groupName) throws UnirestException {
		// checkNotNull(groupName);
		if (groupName == null) {
			return null;
		} else {

			List<Service> serviceList = new ArrayList();

			HttpResponse<JsonNode> response = Unirest.get(yelloPageAdress + groupName).asJson();
			if (response.getStatus() == 200) { // 200 = OK
				JSONArray jsonArray = response.getBody().getObject().getJSONArray("services");

				for (int i = 0; i < jsonArray.length(); i++) {
					System.out.println("jsonA:" + jsonArray.get(i).toString());
					Service dto = getServiceURIByID(jsonArray.get(i).toString());
					serviceList.add(dto);
				}
			}

			return serviceList;
		}
	}

	public static Service getServiceURIByID(String yellowPageID) throws UnirestException {
		// checkNotNull(yellowPageID);

		System.out.println("YelloPageService.java:65 : \n\t ID\t" + yellowPageID);
		System.out.println("\t Adress\t" + yelloPageAdress);
		System.out.println("\t URI\t" + yelloPageAdress + yellowPageID);

		HttpResponse<String> response = Unirest.get("http://172.18.0.5:4567" + yellowPageID).asString();
		if (response.getStatus() == 200) { // 200 = OK

			Gson g = new Gson();
			Service dto = g.fromJson(response.getBody(), Service.class);

			return dto;
		}
		System.err.println("service in yellowpage not found\ninvalid yellowpage ID !!!");
		System.err.println("ID = " + yellowPageID);
		System.err.println("URI = " + yelloPageAdress + yellowPageID);
		throw new RuntimeException("service in yellowpage not found\ninvalid yellowpage ID !!!");
	}

}