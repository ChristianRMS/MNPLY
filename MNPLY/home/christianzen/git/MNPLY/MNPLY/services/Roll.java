package services;

import java.io.Serializable;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class Roll implements Serializable {

	private static final long serialVersionUID = 1337L;
	@Expose
	private int number;

	public Roll() {
		Random rnd = new Random();
		this.number = rnd.nextInt(6) + 1;
	}

	public int getNumber() {
		return this.number;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}