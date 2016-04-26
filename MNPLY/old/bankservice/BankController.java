package bankservice;

import java.util.ArrayList;
import java.util.List;

import services.*;

public class BankController {

	// private static Service service = new Service("Boards", "Board Serivce",
	// "boards",
	// "https://vs-docker.informatik.haw-hamburg.de/ports/19713/banks");
	// private static String yellowPages =
	// "http://vs-docker.informatik.haw-hamburg.de:8053/services";
	// private List<Bank> banks = new ArrayList<>();

	List<Bank> banks = new ArrayList<Bank>();

	public List<String> getBankList() {
		List<String> bankList = new ArrayList();
		for (Bank bank : banks) {
			bankList.add(bank.getID());
		}
		return bankList;
	}

	public void createBank(){
		
	}
}