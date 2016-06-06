package clientservice.Client.controller;

import java.util.ArrayList;
import java.util.List;
import org.json.*;
import com.mashape.unirest.http.Unirest;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import clientservice.Client.util.Service;

public class ClientController {

	static String yelloPageAdress2 = "http://141.22.34.15/cnt/172.18.0.5/4567/services/of/name/";

	static String yelloPageAdress = "http://172.18.0.5:4567/services/of/name/";

	public String gameServiceUri = "";

	public List<String> gamesList = new ArrayList();

	public String playerName = "";
	
	public boolean turn;
	
	

	// client functions:

	
	
	
	/**
	 * @return the turn
	 */
	public boolean isTurn() {
		return turn;
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

	/**
	 * @return the gameServiceUri
	 */
	public String getGameServiceUri() {
		return gameServiceUri;
	}

	/**
	 * @param gameServiceUri the gameServiceUri to set
	 */
	public void setGameServiceUri(String gameServiceUri) {
		this.gameServiceUri = gameServiceUri;
	}

	/**
	 * @param turn the turn to set
	 */
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	/**
	 * @return the gamesList
	 */
	public List<String> getGamesList() {
		return gamesList;
	}

	/**
	 * @param gamesList the gamesList to set
	 */
	public void setGamesList(List<String> gamesList) {
		this.gamesList = gamesList;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		System.out.println(playerName);
	}

	public boolean getGameServiceStatus() {
		GetRequest resp = Unirest.get(gameServiceUri);
		System.out.println(resp.getBody().toString().contains("running"));
		return resp.getBody().toString().contains("running");

		//		HttpResponse<String> response = Unirest.post(g.getComponents().getBank()+"/accounts")
		//                .header("Content-Type","application/json")
		//                .body(gson.toJson(accountDTO)).asString();
	}

	public String findGameService() {
		try {
			// get Services
			List<Service> groupServices = getServicesByGroupName("group_42");
			for (Service service : groupServices) {

				//System.out.println("Service found: " + service.getUri());

				if (service.getService().equals("games"))
					return gameServiceUri = service.getUri();
				System.out.println("gameservice found: " + gameServiceUri);
			}
		} catch (UnirestException ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public String getGameUri() {
		if (gameServiceUri == null || gameServiceUri.equals("")) {
			this.gameServiceUri = findGameService();
			return gameServiceUri;
		} else {
			return "";
		}
	}

	// erstelle ein Spiel:
	public void createGame(String gameName) throws UnirestException {
		Gson gson = new Gson();
		System.out.println("");

		// get gameservice uri from yellowpage

		//		try {
		//			// get Services
		//			List<Service> groupServices = getServicesByGroupName("group_42");
		//			for (Service service : groupServices) {
		//
		//				System.out.println("Service found: " + service.getUri());
		//
		//				if (service.getService().equals("games"))
		//					gameServiceUri = service.getUri();
		//				System.out.println("gameserviceuri: " + gameServiceUri);
		//			}
		//		} catch (UnirestException ex) {
		//			ex.printStackTrace();
		//		}

		// post auf game -> spiel erstellen param: gameName
		System.out.println(gameServiceUri);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", gameName);

		System.out.println(jsonObject.toString());

		if (gameServiceUri != null) {
			HttpResponse<String> jsonResponse = Unirest.post(gameServiceUri).header("Content-Type", "application/json")
					.body(jsonObject).asString();

			System.out.println(jsonResponse.getStatus());

			//			System.out.println(jsonResponse.toString());
			//			JSONObject jsonObject = new JSONObject();
			//			jsonObject.append("name", gameName);
			//			//Service service = new Service();
			//			HttpRequestWithBody resp = Unirest.post(gameServiceUri + "/").header("accept", "application/json")
			//					.body("{\"name\":\"FIRST\"}").asJson();
			//
			//			System.out.println(resp.getStatus());
			//			//System.out.println(request.getBody());
			//			//System.out.println(msg);

			// get header from response and save gameuri in gamesList
		}

	}
	
	public void ready() throws UnirestException {
        Unirest.put(gameServiceUri + "/players/" + getPlayerName() + "/ready").asString();
    }

	public void joinGame(String gameName) {

		if (gameServiceUri == null || gameServiceUri.equals("")) {
			getGameUri();
		}

	}

}