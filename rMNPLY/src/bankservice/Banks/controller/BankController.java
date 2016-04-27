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

	/**
	 * Gets the bank for the given game.
	 * 
	 * @param gameid
	 *            ID of the game from which you want the bank.
	 * @return Bank if the game already owns a bank. null otherwise.
	 */
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

	/**
	 * This method will get the newAccount for a player if it exists.
	 *
	 * @param bank
	 *            The given bank for which the newAccount of this playerid is.
	 * @param playerid
	 *            Is the playerid for which the Account will be found.
	 * @return Returns the Account for a player or null if there isn't a match.
	 */
	public Account getAccount(Bank bank, String playerid) {
		List<Account> Accounts = bank.getBankAccounts();

		for (Account Account : Accounts) {
			if (playerid.equals(Account.getPlayer().getId())) {
				return Account;
			}
		}
		return null;
	}

	/**
	 * This Method will check, that an newAccount is maybe already in use. So
	 * this check helps to create a newAccount only once.
	 *
	 * @param newAccount
	 *            The newAccount which will be checked for existenz.
	 * @param bank
	 *            The bank which will own the given newAccount.
	 * @return Returns true if the given newAccount exists or false when the
	 *         given newAccount doesn't exist.
	 */
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

	/**
	 * This method will return all actual available transfers as a List of
	 * transfers.
	 *
	 * @param transferList
	 *            This list holds every Transfer which is / was available.
	 * @param gameid
	 *            The gameid shows the identifier for the transfers into a game.
	 * @return Returns a list with all available transfers.
	 */
	// public List<Transfer> getAllAvailableTransfers(TransferList transferList,
	// String gameid) {
	// Map<String, Transfer> transferMap = transferList.getTransfers();
	// List<Transfer> transfers = new ArrayList<>();
	//
	// for (Transfer transfer : transferMap.values()) {
	// transfers.add(transfer);
	// }
	//
	// return transfers;
	// }
}
