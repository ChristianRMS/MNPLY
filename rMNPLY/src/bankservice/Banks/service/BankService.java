package bankservice.Banks.service;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.org.apache.bcel.internal.classfile.Attribute;

import bankservice.Banks.controller.BankController;
import bankservice.Banks.entities.*;
import bankservice.Banks.util.*;
import util.ServiceTemplateBank;

/**
 * created by Christian Zen christian.zen@outlook.de Date of creation:
 * 26.04.2016
 */
public class BankService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BankController bankController = new BankController();

		get(("/banks"), (req, res) -> {
			res.status(200);
			return 200;
		});

		/*
		 * creates a new bank
		 */
		post(("/banks"), (req, res) -> {
			String gameId = req.attribute("gameId");
			Bank newBank = new Bank(gameId);
			bankController.addBankToList(newBank);
			res.status(200);
			return newBank;
		});

		/*
		 * create new account post /banks/{gameid}/players
		 */
		post(("/banks/:gameId/players"), (req, res) -> {
			Bank bank = bankController.getBank(req.attribute("gameId"));
			String account = req.attribute("account");

			if (bank == null) {
				throw new IllegalArgumentException("no bank found");
			}

			// if (bankController.containsBank(account, bank, bankList)) {
			// throw new IllegalArgumentException("account already in use");
			// }
			Account acc = new Account();
			bank.addBankAccount(acc);
			res.status(200);
			return acc;
		});

		// - experimental : still to test

		/*
		 * Kontostand abfragen get /banks/{gameid}/players/{playerid}
		 */
		get(("/:gameId}/players/:playerId"), (req, res) -> {
			Bank bank = bankController.getBank(req.attribute("gameId"));
			if (bank == null) {
				throw new IllegalArgumentException("no bank found");
			}
			String playerId = req.attribute("playerId");
			Account bankAccount = bankController.getAccount(bank, playerId);
			return bankAccount.getSaldo();
		});

		/*
		 * Geld von der Bank überwiesen werden kann mit post
		 * /banks/{gameid}/transfer/to/{to}/{amount}
		 */
		post(("/:gameId/transfer/to/:to/:amount"), (req, res) -> {

			int amount = req.attribute("amount");
			String gameId = req.attribute("gameId");

			Bank bank = bankController.getBank(gameId);
			String to = req.attribute("to");

			if (bank == null) {
				throw new IllegalArgumentException("no bank found");
			}

			Account bankAccount = bankController.getAccount(bank, to);

			return bankController.transfer(gameId, null, bankAccount, amount);
		});

		/*
		 * Geld eingezogen werden kann mit post
		 * /banks/{gameid}/transfer/from/{from}/{amount}
		 */
		post(("/:gameId/transfer/from/:from/:amount"), (req, res) -> {
			String gameid = req.attribute("gameId");
			String from = req.attribute("gameId");
			int amount = req.attribute("amount");
			Bank bank = bankController.getBank(gameid);

			if (bank == null) {
				throw new IllegalArgumentException("no bank found");
			}

			Account bankAccount = bankController.getAccount(bank, from);

			if (bankAccount.getSaldo() < amount)
				throw new IllegalArgumentException("not enough money in account");

			return bankController.transfer(gameid, bankAccount, null, amount);
		});

		/*
		 * Geld von einem zu anderen Konto übertragen werden kann mit post
		 * /banks/{gameid}/transfer/from/{from}/to/{to}/{amount}
		 */

		try {
			Unirest.post("http://172.18.0.5:4567/services").header("Content-Type", "application/json")
					.queryString("name", "Bank Service").queryString("description", "CI Bank Service")
					.queryString("service", "Bank").queryString("uri", ConstantsBank.BANKSERVICE + "/")
					.body(new Gson().toJson(
							new ServiceTemplateBank("Bank", "CI Bank Service", "Bank", ConstantsBank.BANKSERVICE)))
					.asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
