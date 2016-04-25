package bankservice;

public class Account {

	private String playerID = "";
	private int saldo = 100;

	public Account(String playerID) {
		this.playerID = playerID;
	}

	public String getID() {
		return playerID;
	}

	public int getSaldo() {
		return saldo;
	}

	public boolean setSaldo(int saldo) {
		try {
			this.saldo = saldo;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}