import database.FilmDAOEnum;

public class sandbox {

	public static void main(String[] args) {
		
		FilmDAOEnum DAO = FilmDAOEnum.INSTANCE;
		
		DAO.getConnection();
		
		
		
	}

}
