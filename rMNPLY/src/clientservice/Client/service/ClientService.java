package clientservice.Client.service;

import bankservice.Banks.util.IpFinder;
import clientservice.Client.controller.ClientController;

import static spark.Spark.get;

import javax.swing.JFrame;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientService {

	public static int port = 4567;
	public static String ip = IpFinder.getIP();
	public static String URL = "http://" + ip + ":" + port;
	public static String URLService = URL + "/client";

	public static ClientController clientController = new ClientController();

	public static void main(String[] args) throws UnirestException {

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
			res.header("Location", URLService + clientController.getPlayerName());
			res.status(200);
			return clientController.getPlayerName();
		});

		/*
		 * post /client/turn
		 * Informs the player, that it is his turn 
		 */
		post(("/client/turn"), (req, res) -> {
			res.header("Content-Type", "application/json");
			res.status(200);
			
			return res.status();
		});

		// join game

		/*
		 * lost update unrepeatable read (esoterik) dirty read (folien,
		 * exemplarisch implementieren)
		 */

		/**
		 * GUI init.
		 */

		Surface surface = new Surface(clientController);
		surface.setVisible(true);
		surface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		surface.setSize(600, 600);

	}

}
