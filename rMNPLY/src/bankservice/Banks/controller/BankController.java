package bankservice.Banks.controller;

import java.util.ArrayList;
import java.util.List;

import bankservice.Banks.entities.*;

/**
 * created by Christian Zen 
 * christian.zen@outlook.de 
 * Date of creation:
 * 26.04.2016
 */
public class BankController {

	public BankController() {
	}

	List<Bank> bankList = new ArrayList();

	public void addBankToList(Bank bank) {
		bankList.add(bank);
	}

	public Bank getBank(String gameId) {
		for (Bank bank : bankList) {
			if (bank.getGameId() == gameId) {
				return bank;
			}
		}
		return null;
	}

	public boolean containsBank(Account account, Bank bank, List<Bank> banks) {
		for (Bank b : banks) {
			for (Account acc : b.getBankAccounts()) {
				if (account.getPlayer().equals(acc.getPlayer())) {
					return true;
				}
			}
		}
		return false;
	}

	public Account getAccount(Bank bank, String playerid) {
		List<Account> Accounts = bank.getBankAccounts();

		for (Account Account : Accounts) {
			if (playerid.equals(Account.getPlayer().getId())) {
				return Account;
			}
		}
		return null;
	}

	public boolean isAccountExisting(Account newAccount, Bank bank) {
		boolean AccountExists = false;

		String playerID = newAccount.getPlayer().getId();
		Account alreadyAvailableAccount = getAccount(bank, playerID);
		if (alreadyAvailableAccount != null) {
			AccountExists = true;
		}
		return AccountExists;
	}

	public Bank createBank(String gameId) {
		Bank newBank = new Bank(gameId);
		bankList.add(newBank);
		return newBank;
	}
}
