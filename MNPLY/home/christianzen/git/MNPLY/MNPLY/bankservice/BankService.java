package bankservice;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import services.Service;
//import game.*;
//import services.Constants;
import spark.Spark;
import util.Constants;

public class BankService {

	public static void main(String[] args) {

		Gson gson = new Gson();
		GsonBuilder gsonBuilder = new GsonBuilder();
		BankController bankController = new BankController();
		
		
		
		//ArrayList<Bank> bankList = new ArrayList<Bank>();

		// List of available banks
		get("/banks", (req, res) -> {
			return bankController.getBankList();
		});
		
//		put("/banks/:bankId", (req, res) -> {
//			res.header("Content-Type", "application/json");
//			bankController.createBank(req.params(":bankId"));
//			
//			Bank bank = new Bank(req.params(":gameID"));
//			bankList.add(bank);
//			return gson.toJson(bank);
//		});

		// ein Konto erstellt werden kann mit
		// post /banks/{gameid}/players
		post("/banks/:gameid/players", (req, res) -> {
			res.status(201);
			res.header("Content-Type", "application/json");
			Bank bank = new Bank(req.params(":gameid"));
			// for (Bank banks : bankList) {
			// if (banks.getID().equals(req.params(":gameid")))
			// bank = banks;
			// }
			// if (bank.getAccountBy(req.body()) != null) {
			// res.status(409);
			// return "player already got a bank account";
			// }
			res.status(201);
			// HttpResponse playerResponse = Unirest
			// .get(Constants.GAMESERVICE + "/games/" + req.params(":gameid") +
			// "/players/" + req.body()).asJson();
			// Player player =
			// gson.fromJson(playerResponse.getBody().toString(), Player.class);

			// Account account = new Account(req.body());
			// bank.addAccount(account.getID());
			return gson.toJson(bank, Bank.class);
		});

		try {
			Unirest.post("http://172.18.0.5:4567/services").header("Content-Type", "application/json")
					.queryString("name", "Bank Service").queryString("description", "CI Bank Service")
					.queryString("service", "Bank").queryString("uri", Constants.BANKSERVICE + "/")
					.body(new Gson().toJson(new Service("Bank", "CI Bank Service", "Bank",
							Constants.BANKSERVICE)))
					.asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

	}
}

// public Bank() {
//
// get("/banks", (req, res) -> {
// return "";
// });
//
// get("/banks/:gameID", (req, res) -> {
//
// String gameID = req.params(":gameID");
// String tmpBank = null;
//
// for (Bank bank : bankList) {
// if (bank.getID() == gameID) {
// tmpBank = bank.toString();
// bankList.add(bank);
// }
// }
//
// if (tmpBank == null) {
// res.status(404);
// return "Resource could not be found";
// } else {
// res.status(200);
// return gson.fromJson(tmpBank, Bank.class);
// }
// });
//
// /*
// * PUT /banks/{gameid} places a banks
// */
//
// put("/banks/:gameID", (req, res) -> {
// res.header("Content-Type", "application/json");
// Bank bank = new Bank(req.params(":gameID"));
// bankList.add(bank);
// return gson.toJson(bank);
// });
//
// post("/banks/:gameid/players/:playerid", (req, res) -> {
//
// Account acc = new Account(req.params(":playerid"));
//
// for (Bank bank : bankList) {
// if (bank.getID() == req.params(":gameid")) {
// bank.addAccount(req.params(":playerID"));
// }
// }
//
// res.header("Location", req.body());
//
// res.status(200);
// return "";
// });
//
// /*
// * POST /banks/{gameid}/players creates a bank account
// */
//
// post("/banks/:gameid/players", (req, res) -> {
// res.status(201);
// res.header("Content-Type", "application/json");
// Bank bank = null;
// for (Bank banks : bankList) {
// if (banks.getID().equals(req.params(":gameid")))
// bank = banks;
// }
// if (bank.getAccountBy(req.body()) != null) {
// res.status(409);
// return "player already got a bank account";
// }
// res.status(201);
// HttpResponse playerResponse = Unirest
// .get(Constants.GAMESERVICE + "/games/" + req.params(":gameid") + "/players/"
// + req.body()).asJson();
// Player player = gson.fromJson(playerResponse.getBody().toString(),
// Player.class);
//
// Account account = new Account(req.body());
// bank.addAccount(account.getID());
// return gson.toJson(account);
// });
//
// /*
// * GET /banks/{gameid}/players/{playerid} returns the saldo of the
// * player
// */
//
// get("/banks/:gameID/players/:playerID", (req, res) -> {
//
// String gameID = req.params("gameID");
// String playerID = req.params("playerID");
// // HttpResponse gameResponse = Unirest.get(Constants.GAMESERVICE +
// // "/games/" + req.params(":gameID")).asJson()
//
// Bank bank = null;
// Account acc = null;
//
// for (Bank banks : bankList) {
// if (banks.getID().equals(gameID))
// bank = banks;
// }
//
// if (bank != null) {
// acc = bank.getAccountBy(playerID);
// }
//
// // String gamestr =
// //
// gsonBuilder.excludeFieldsWithoutExposeAnnotation().create().toJson(gameResponse,
// // Game.class);
// //
// // if (game == null) {
// // res.status(404);
// // return "Spiel nicht gefunden";
// // }
// //
// // if (game.getPlayerByID(playerID).equals(null)) {
// // res.status(404);
// // return "Spieler nicht gefunden";
// // }
//
// int amount = acc.getSaldo();
//
// res.status(200);
//
// return gson.toJson(amount, Integer.class);
// });
//
// /*
// * /banks/{gameid}/transfer/to/{to}/{amount} creates a new bank transfer
// * from the bank itself
// */
//
// post("/banks/:gameid/transfer/to/:to/:amount", (req, res) -> {
// res.status(201);
// res.header("Content-Type", "application/json");
// Bank bank = null;
// for (Bank b : bankList) {
// if (b.getID().equals(req.params(":gameid")))
// bank = b;
// }
// Account to = bank.getAccountBy(req.params(":to"));
// int saldo = to.getSaldo();
// to.setSaldo(saldo + Integer.parseInt(req.params(":amount")));
//
// return gson.toJson(to);
// });
//
// /*
// * /banks/{gameid}/transfer/from/{from}/{amount} creates a new bank
// * transfer to the bank itself
// */
// post("/banks/:gameid/transfer/from/:from/:amount", (req, res) -> {
// res.status(201);
// res.header("Content-Type", "application/json");
// Bank bank = null;
// for (Bank b : bankList) {
// if (b.getID().equals(req.params(":gameid")))
// bank = b;
// }
// Account from = bank.getAccountBy(req.params(":from"));
// if (from.getSaldo() < Integer.parseInt(req.params(":amount"))) {
// res.status(403);
// return "insufficient fonds";
// }
// int saldo = from.getSaldo();
// from.setSaldo(saldo - Integer.parseInt(req.params(":amount")));
//
// return gson.toJson(from, Player.class);
// });
// }

// public static void main(String[] args) {
//
// Spark.port(4567);
//
// new BankService();
//
