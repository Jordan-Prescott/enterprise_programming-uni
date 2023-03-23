package models;

public class Joke {
	
	String id, type, setup, punchline;

	public Joke(String id, String type, String setup, String punchline) {
		this.id = id;
		this.type = type;
		this.setup = setup;
		this.punchline = punchline;
	}
	
	public Joke() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getPunchline() {
		return punchline;
	}

	public void setPunchline(String punchline) {
		this.punchline = punchline;
	}

}
