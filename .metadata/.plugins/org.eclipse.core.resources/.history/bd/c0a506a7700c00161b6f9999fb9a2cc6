package util;

import bankservice.Account;
import bankservice.Bank;

public class Transaction {

	public boolean transferFrom(Bank bank, Account account, int amount) {

		int bankMoney = bank.getBankAmount();
		int playerMoney = account.getSaldo();

		boolean successSetPlayerMoney = account.setSaldo(playerMoney - amount);

		if (!successSetPlayerMoney) {
			return false;
		}

		boolean successSetBankMoney = bank.setBankAmount(bankMoney + amount);

		if (!successSetBankMoney) {
			return false;
		}
		return true;
	}

	public boolean transferTo(Bank bank, Account account, int amount) {
		int bankMoney = bank.getBankAmount();
		int playerMoney = account.getSaldo();

		boolean successSetBankMoney = bank.setBankAmount(bankMoney - amount);

		if (!successSetBankMoney) {
			return false;
		}

		boolean successSetPlayerMoney = account.setSaldo(playerMoney + amount);

		if (!successSetPlayerMoney) {
			return false;
		} else {
			return true;
		}
	}
}
