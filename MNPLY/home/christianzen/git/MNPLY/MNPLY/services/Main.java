package services;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class Main {

	public static void main(String[] args) {
		get("/hello", (req, res) -> "Hello World");
	}
}
