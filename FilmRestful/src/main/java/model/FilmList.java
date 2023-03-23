package model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "films")
public class FilmList {

	@XmlElement(name = "film")
	private List<Film> contactsList;

	public FilmList() {
	}

	public FilmList(List<Film> contactsList) {
		this.contactsList = contactsList;
	}

	public List<Film> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Film> contactsList) {
		this.contactsList = contactsList;
	}
}
