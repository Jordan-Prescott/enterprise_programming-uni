package model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

// JAXB Annotations: JAXB Container class. Array -> XML
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "films") // root XML element 

/**
 * FilmList
 * 
 * @author jordanprescott
 *
 * Array of Film objects.
 * Used also as container class for JAXB.
 * 
 * @version 1.0
 * @since 06/04/23
 * 
 */
public class FilmList {

	@XmlElement(name = "film") // XML definition of element used
	private List<Film> filmsList;

	public FilmList() {
	}

	public FilmList(List<Film> filmsList) {
		this.filmsList = filmsList;
	}

	public List<Film> getFilmsList() {
		return filmsList;
	}

	public void setFilmsList(List<Film> filmsList) {
		this.filmsList = filmsList;
	}
}
