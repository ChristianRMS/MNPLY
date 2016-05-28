package userservice.User.service;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import bankservice.Banks.util.ConstantsBank;
import bankservice.Banks.util.IpFinder;
import userservice.User.controller.UserController;
import userservice.User.entities.User;
import util.ServiceTemplateBank;

/**
 * created by Christian Zen christian.zen@outlook.de Date of creation:
 * 26.04.2016
 */
public class UserService {

	public static int port = 4567;
	public static String ip = IpFinder.getIP();
	public static String URL = "http://" + ip + ":" + port;
	public static String URLService = URL + "/users";

	/*
	 * The users service registers users of the system
	 */

	public static void main(String[] args) {

		UserController userController = new UserController();

		List<User> userList = new ArrayList<User>();

		/*
		 * Returns list of URIs of player resources
		 */
		get(("/users"), (req, res) -> {
			Gson gson = new Gson();
			List<String> usersStringList = new ArrayList<>();
			for (User user : userList) {
				if (user.getName() != null) {
					usersStringList.add("/users/" + user.getName());
				}
			}
			res.status(200);
			return "{\"users\" : " + gson.toJson(userList) + "}";
		});

		/*
		 * Registers a new player with the system
		 */
		post(("/users"), (req, res) -> {

			JSONObject jsonObject = new JSONObject(req.body());
			String name = jsonObject.getString("name");
			String uri = jsonObject.getString("uri");
			String id = "/user/" + name;

			for (User user : userList) {
				if (user.getName().equals(name)) {
					res.status(412); // Precondition Failed
					throw new IllegalArgumentException();
				}
			}
			User newUser = new User(id, name, uri);
			userList.add(newUser);
			res.status(201); // created

			// setLocationHeader
			res.header("Location", name);

			return newUser.toString();
		});

		/*
		 * Returns the state of the player resource
		 */
		get(("/users/:userId"), (req, res) -> {
			String userId = req.attribute("userId");
			User theUser;
			for (User user : userList) {
				if (user.getId() == userId) {
					res.status(200);
					return user;
				}
			}
			res.status(412);
			return null;
		});

		put(("/users/:userId"), (req, res) -> {
			String userId = req.params(":userId");
			User user = null;

			// find user
			for (User u : userList) {
				if (u.getName().equals(userId)) {
					user = u;
				}
			}
			if (user == null) {
				res.status(400);
				return res;
			} else {
				// edit user
				// todo :)
			}
			return user;
		});

		/*
		 * Yellow Page Service
		 */

		try {
			Unirest.post("http://172.18.0.5:4567/services").header("Content-Type", "application/json")
					.queryString("name", "group_42").queryString("description", "CI users service")
					.queryString("service", "users").queryString("uri", URLService)
					.body(new Gson()
							.toJson(new ServiceTemplateBank("group_42", "CI users service", "users", URLService)))
					.asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		/*
		 * todo: - Registers or changes the user/player (put) - Unregisters the
		 * user (delete)
		 */

	}

}
