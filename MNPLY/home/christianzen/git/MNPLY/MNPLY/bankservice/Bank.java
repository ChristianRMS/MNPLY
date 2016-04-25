package bankservice;

import java.util.ArrayList;
import java.util.List;

import util.Transaction;

public class Bank {

	private String gameID = "";

	List<Account> playerAccountList = new ArrayList<Account>();
	private int accountAmount = 1000;

	Transaction transaction = new Transaction();

	public Bank(String gameID) {
		this.gameID = gameID;
	}

	public int getBankAmount() {
		return accountAmount;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public boolean transferPull(String playerID, int amount, String reason) {
		return transaction.transferFrom(this, getAccountBy(playerID), amount);
	}

	public boolean transferPush(String playerID, int amount, String reason) {
		return transaction.transferTo(this, getAccountBy(playerID), amount);
	}

	public boolean setBankAmount(int accountAmount) {
		try {
			this.accountAmount = accountAmount;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Account getAccount(String playerid) {
		for (Account account : playerAccountList) {
			if (account.getID().equals(playerid))
				return account;
		}
		return null;
	}

	public String getID() {
		return gameID;
	}

	public boolean addAccount(String playerID) {
		// precondition: for not dublication accounts
		for (Account account : playerAccountList) {
			if (account.getID().compareTo(playerID) == 0) {
				return false;
			}
		}
		return playerAccountList.add(new Account(playerID));
	}

	public boolean removeAccount(String playerID) {
		for (Account account : playerAccountList) {
			if (account.getID().compareTo(playerID) == 0) {
				return playerAccountList.remove(playerID);
			}
		}
		return false;
	}

	public List<Account> getAccountList() {
		return playerAccountList;
	}

	public Account getAccountBy(String playerID) {
		for (Account account : playerAccountList) {
			if (account.getID().compareTo(playerID) == 0) {
				return account;
			}
		}
		return null;
	}
}