package clientservice.Client.service;

import bankservice.Banks.util.IpFinder;
import clientservice.Client.controller.ClientController;

import static spark.Spark.get;

import com.mashape.unirest.http.Unirest;

public class ClientService {

	public static int port = 4567;
	public static String ip = IpFinder.getIP();
	public static String URL = "http://" + ip + ":" + port;
	public static String URLService = URL + "/client";

	public static ClientController clientController = new ClientController();

	public static void main(String[] args) {

		/*
		 * URl: /client A service which acts as a representant of a
		 * player/client. The endpoint may be found at any other uri, but it
		 * must offer the described mehtods and the full uri must be submitted
		 * when registering a player
		 */

		// get /client
		// gets the details about the player
		// Content-Type: application/json
		get(("/client"), (req, res) -> {
			res.header("Content-Type", "application/json"); // ?

			// creates a new game. Values may optionaly be set within the body
			// parameter.
			// Unirest.post(url); -> get group_42 gameservice
			// String gameServiceUri = Unirest.get(url)

			// unirest post: auf gameservice, parameter spielname

			// unirest get: auf gameservice, get uri of game

			res.status(200);
			return null;
		});

		// join game

		/*
		 * lost update unrepeatable read (esoterik) dirty read (folien,
		 * exemplarisch implementieren)
		 */

		clientController.createGame("fu");

	}

}
