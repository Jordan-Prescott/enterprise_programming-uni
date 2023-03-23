package models;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "contacts")
public class ContactList {

	@XmlElement(name = "contact")
	private List<Contact> contactsList;

	public ContactList() {
	}

	public ContactList(List<Contact> contactsList) {
		this.contactsList = contactsList;
	}

	public List<Contact> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Contact> contactsList) {
		this.contactsList = contactsList;
	}
}
