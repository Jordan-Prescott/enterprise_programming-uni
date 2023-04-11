package model;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Film
 * 
 * @author jordanprescott
 *
 * @version 1.0
 * @since 06/04/23
 * 
 */
@XmlRootElement(name = "film") // JAXB annotation: root element
public class Film {

	public Film(int id, String title, int year, String director, String stars, String review, String genre, String rating) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.stars = stars;
		this.review = review;
		this.genre = genre; // added for extra functionality
		this.rating = rating; // added for extra functionality
		
	}

	public Film() {
		super();
	}

	int id;
	String title;
	int year;
	String director;
	String stars;
	String review;
	String genre;
	String rating;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}


	@Override
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", year=" + year + ", director=" + director + ", stars=" + stars
				+ ", review=" + review + ", genre=" + genre + ", rating=" + rating + "]";
	}
}
