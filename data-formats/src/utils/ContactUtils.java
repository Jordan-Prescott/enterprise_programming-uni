package utils;

import java.util.ArrayList;

import models.Contact;


public class ContactUtils {

	private static ArrayList<Contact> contacts = new ArrayList<>();
	
	static {
		contacts.add(new Contact(1, "Mohammed Kaleem" , "kaleem@mail.com"));
		contacts.add(new Contact(2, "Kris Welsh" , "kris@mail.com"));
		contacts.add(new Contact(3, "Keeley Crokett" , "keeley@mail.com"));
		contacts.add(new Contact(4, "Nick Whitaker" , "nick@mail.com"));
		contacts.add(new Contact(5, "Alan Crispin" , "alan@mail.com"));
	}
	
	
	public static ArrayList<Contact> getAllContacts() {
		return contacts;
	}
	
}
