package clientservice.Client.controller;

import java.net.URISyntaxException;
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
import clientservice.Client.util.URIObject;
import clientservice.Client.util.URIParser;

public class ClientController {

	static String yelloPageAdress2 = "http://141.22.34.15/cnt/172.18.0.5/4567/services/of/name/";

	static String yelloPageAdress = "http://172.18.0.17:4567/services/of/name/";

	public String gameServiceUri = "";

	public List<String> gamesList = new ArrayList();

	public String playerName = "";

	public boolean turn;

	public String usersService;

	public void setUsersService(String usersS) {
		this.usersService = usersS;
	}

	public String getUsersService() {
		if (usersService == null || usersService.equals("")) {
			findUsersService();
		}
		return usersService;
	}

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

		HttpResponse<String> response = Unirest.get("http://172.18.0.17:4567" + yellowPageID).asString();
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
		if (gameServiceUri == null || gameServiceUri.equals("")) {
			findGameService();
		}
		return gameServiceUri;
	}

	/**
	 * @param gameServiceUri
	 *            the gameServiceUri to set
	 */
	public void setGameServiceUri(String gameServiceUri) {
		this.gameServiceUri = gameServiceUri;
	}

	/**
	 * @param turn
	 *            the turn to set
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
	 * @param gamesList
	 *            the gamesList to set
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
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		System.out.println(playerName);
	}

	public boolean getGameServiceStatus() {
		GetRequest resp = Unirest.get(gameServiceUri);
		System.out.println(resp.getBody().toString().contains("running"));
		return resp.getBody().toString().contains("running");

		// HttpResponse<String> response =
		// Unirest.post(g.getComponents().getBank()+"/accounts")
		// .header("Content-Type","application/json")
		// .body(gson.toJson(accountDTO)).asString();
	}

	public String findGameService() {
		try {
			// get Services
			List<Service> groupServices = getServicesByGroupName("group_42");
			for (Service service : groupServices) {

				// System.out.println("Service found: " + service.getUri());

				if (service.getService().equals("games")) {
					setGameServiceUri(service.getUri());
					return service.getUri();

				}

				// return gameServiceUri = service.getUri();
				// System.out.println("gameservice found: " + gameServiceUri);
			}
		} catch (UnirestException ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public String findUsersService() {
		try {
			// get Services
			List<Service> groupServices = getServicesByGroupName("group_42");
			for (Service service : groupServices) {

				// System.out.println("Service found: " + service.getUri());

				if (service.getService().equals("users")) {
					setUsersService(service.getUri());
				}

				// System.out.println("userservice found: " +
				// getUsersService());
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
	public String createGame(String gameName) throws UnirestException {
		Gson gson = new Gson();
		System.out.println("");

		// get gameservice uri from yellowpage

		// try {
		// // get Services
		// List<Service> groupServices = getServicesByGroupName("group_42");
		// for (Service service : groupServices) {
		//
		// System.out.println("Service found: " + service.getUri());
		//
		// if (service.getService().equals("games"))
		// gameServiceUri = service.getUri();
		// System.out.println("gameserviceuri: " + gameServiceUri);
		// }
		// } catch (UnirestException ex) {
		// ex.printStackTrace();
		// }

		// post auf game -> spiel erstellen param: gameName
		System.out.println(gameServiceUri);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", gameName);

		System.out.println(jsonObject.toString());

		if (gameServiceUri != null) {
			HttpResponse<String> jsonResponse = Unirest.post(gameServiceUri).header("Content-Type", "application/json")
					.body(jsonObject).asString();

			System.out.println(jsonResponse.getStatus());

			return Integer.toString(jsonResponse.getStatus());

			// System.out.println(jsonResponse.toString());
			// JSONObject jsonObject = new JSONObject();
			// jsonObject.append("name", gameName);
			// //Service service = new Service();
			// HttpRequestWithBody resp = Unirest.post(gameServiceUri +
			// "/").header("accept", "application/json")
			// .body("{\"name\":\"FIRST\"}").asJson();
			//
			// System.out.println(resp.getStatus());
			// //System.out.println(request.getBody());
			// //System.out.println(msg);

			// get header from response and save gameuri in gamesList
		} else {
			return "";
		}

	}

	public void ready() throws UnirestException {
		Unirest.put(gameServiceUri + "/players/" + getPlayerName() + "/ready").asString();
	}

	public String createUserAccount(String playerName, String clientUri) throws Exception {

		findUsersService();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", playerName);
		jsonObject.put("uri", clientUri);
		System.out.println("usersservice: " + usersService);
		HttpResponse<String> response = Unirest.post(getUsersService()).header("Content-Type", "application/json")
				.body(jsonObject).asString();

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			return response.getHeaders().getFirst("Location");
		} else {
			System.out.println("clientUri: " + clientUri);
			System.out.println("playerName: " + playerName);
			System.out.println(response.getStatus());
			System.out.println(response.getBody());
			System.err.println("Account konnte nicht angelegt werden");
			throw new Exception("Account konnte nicht angelegt werden");
		}
	}

	public List<String> getExistingGames() throws Exception {
		List<String> result = new ArrayList();
		HttpResponse<JsonNode> response = Unirest.get(gameServiceUri).asJson();
		if (response.getStatus() == 200) {
			for (Object o : response.getBody().getArray()) {
				JSONObject jsonObject = new JSONObject(o.toString());
				result.add(jsonObject.getString("id"));
			}
			return result;
		} else {
			throw new Exception("get games failure");
		}
	}

	public URIObject joinGame(String loc, String userAccUri) throws URISyntaxException, UnirestException {

		if (gameServiceUri == null || gameServiceUri.equals("")) {
			getGameUri();
		}

		URIObject gameUriObj = URIParser.createURIObject(gameServiceUri);
		String gameServiceHost = gameUriObj.getHost();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user", userAccUri);

		// System.out.println("GSSH: " + gameServiceHost);
		 System.out.println("LOC: " + loc);
		 System.out.println("userAccUri: "+ userAccUri);
		 

		HttpResponse<String> response = Unirest.post(gameServiceHost + "/" + loc + "/players")
				.header("Content-Type", "application/json").body(jsonObject.toString()).asString();

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			URIObject res = URIParser.createURIObject(response.getHeaders().getFirst("Location"));
			return res;
		} else {
			return null;
		}

	}

}