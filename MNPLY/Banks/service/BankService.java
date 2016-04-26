package service;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import controller.BankController;
import entities.Account;
import entities.Bank;
import services.Service;
import util.Constants;

/**
 * created by Christian Zen
 * christian.zen@outlook.de
 * Date of creation: 26.04.2016
 */
public class BankService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<Bank> bankList = new ArrayList();
		
		BankController bankController = new BankController();
		
		/*
		 * creates a new bank
		 */
		post(("/banks"), (req,res) -> {
			String gameId = req.attribute("gameId");
			Bank newBank = new Bank(gameId);
			bankList.add(newBank);
			res.status(200);
			return newBank;
		});
		
		/*
		 * create new account
		 */
		post(("/banks/:gameId/players"), (req,res) -> {
			Bank bank = bankController.getBank(req.attribute("gameId"), bankList);
			String account = req.attribute("account");

	        if (bank == null) {
	            throw new IllegalArgumentException("no bank found");
	        }

//	        if (bankController.containsBank(account, bank, bankList)) {
//	            throw new IllegalArgumentException("account already in use");
//	        }
	        Account acc = new Account();
	        bank.addBankAccount(acc);
	        res.status(200);
	        return acc;
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
