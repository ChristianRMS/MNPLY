package clientservice.Client.service;

import bankservice.Banks.util.IpFinder;

public class ClientService {

	public static int port = 4567;
	public static String ip = IpFinder.getIP();
	public static String URL = "http://" + ip + ":" + port;
	public static String URLService = URL + "/client";

	public static void main(String[] args) {

	}

}
