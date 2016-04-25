package entities;

import com.google.gson.annotations.Expose;



public class Player {

	@Expose
	private String id;
	@Expose
	private String name;
	@Expose
	private String uri;
	@Expose
	private boolean ready = false;
	@Expose
	private Integer position;
	
	public void setPosition(Integer position){
		this.position = position;
	}
	
	public Integer getPosition(){
		return position;
	}

	public Player(String playerId) {
		this.id = playerId;
		this.name = "player" + playerId;
	}

	public Player(String id, String name, String uri) {
		this.uri = uri;
		this.name = name;
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	// public Place getPlace() {
	// return place;
	// }
	//
	// public void setPlace(Place place) {
	// this.place = place;
	// }
	//
	// public int getPosition() {
	// return position;
	// }
	//
	// public void setPosition(int position) {
	// this.position = position;
	// }

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

}