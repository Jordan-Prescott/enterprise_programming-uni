package models;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Dr Mohammed Kaleem
 * Lab Work
 */


@XmlRootElement(name = "contact")
public class Contact {
	
	public Contact() {
		
	}

	private int id;
	private String name; 
	private String email; 
	
	public Contact(int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	
	public Contact(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}



	//@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}


	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
