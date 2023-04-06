import java.util.ArrayList;
import java.util.Iterator;

import database.FilmDAOEnum;
import model.Film;

public class sandbox {

	public static void main(String[] args) {

		FilmDAOEnum DAO = FilmDAOEnum.INSTANCE;

		ArrayList<Film> filmsArray = DAO.getAllFilms();

		Iterator iter = filmsArray.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}

	}

}
