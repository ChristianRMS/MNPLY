package clientservice.Client.service;

import bankservice.Banks.util.IpFinder;
import clientservice.Client.controller.ClientController;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Timer;

import javax.swing.JFrame;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientService {

	public static int port = 4567;
	public static String ip = IpFinder.getIP();
	public static String URL = "http://" + ip + ":" + port;
	public static String URLService = URL + "/client";
	public static ClientController clientController = new ClientController();
	public static Surface surface;

	public static void main(String[] args)
			throws UnirestException, FileNotFoundException, UnsupportedEncodingException, InterruptedException {

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
			clientController.setTurn(true);
			/**
			 * todo: check if correct player
			 */
			res.status(200);
			return res.toString();
		});

		// recieve event
		//		post(("/client/event"), (req,res) -> {
		//			
		//		});

		/*
		 * lost update unrepeatable read (esoterik) dirty read (folien,
		 * exemplarisch implementieren)
		 */

		/**
		 * GUI init.
		 */

		//Surface surface = new Surface(clientController);
		surface = new Surface(clientController);
		surface.setVisible(true);
		surface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		surface.setSize(600, 600);

		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {
					testEventsLog();
				} catch (FileNotFoundException | UnsupportedEncodingException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 10000);

	}

	public static void testEventsLog()
			throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
		//PrintWriter writer = new PrintWriter("game.txt", "UTF-8");
		for (int i = 0; i < 100; i++) {
			//Timer timer = new Timer();
			//timer.wait(500);
			surface.writeEvent("event: " + Math.random());
			//writer.println("entry: " + Math.random());
		}
		//writer.close();
	}
}
