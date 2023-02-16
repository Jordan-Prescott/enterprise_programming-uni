package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Contact;

/**
 * Dr Mohammed Kaleem
 * Lab Work
 */
public class ContactsDAO {
	Connection c = null;
	Statement s = null;
	ResultSet r = null;

	/**
	 * Get Database Connection
	 * 
	 * @return Statement Object
	 */
	private Statement getConnection() {

		try {
			String conString1 = "jdbc:sqlite:" + this.getClass().getResource("/").getPath() + "contactsdb.sqlite";
			String conString2 = "jdbc:sqlite:" + "/Users/jordanprescott/Developer/enterprise_programming_eclipse_workspace/contacts-db-app/src/main/webapp/WEB-INF/classes/" + "contactsdb.sqlite";
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection(conString2);
			s = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}

		return s;
	}

	/**
	 * Close any open database connection
	 * 
	 */
	private void closeConnection() {
		try {
			if (s != null) {
				s.close();
			}
			if (c != null) {
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieve all Contacts
	 * 
	 * @return Collection of all contacts found in the sqlite database
	 */
	public ArrayList<Contact> selectAllContacts() {

		ArrayList<Contact> contactsArray = new ArrayList<Contact>();
		try {
			String sql = "select * from  contacts";
			ResultSet rs = getConnection().executeQuery(sql);

			if (rs != null) {
				while (rs.next()) {

					Contact contact = new Contact();

					try {

						contact.setId(rs.getInt("id"));
						contact.setName(rs.getString("Name"));
						contact.setEmail(rs.getString("Email"));
						System.out.println(contact.getName());

					} catch (SQLException s) {
						s.printStackTrace();
					}

					contactsArray.add(contact);
				}

				rs.close();
			}
		} catch (SQLException s) {
			System.out.println(s);
		}

		closeConnection();
		return contactsArray;
	}

	/**
	 * Retrieve Contact by ID
	 * 
	 * @return Collection of all contacts found in the sqlite database
	 */
	public Contact selectContacts(int id) {

		Contact c = new Contact();

		try {
			String sql = "select * from  contacts WHERE id = " + id + ";";
			ResultSet rs = getConnection().executeQuery(sql);

			if (rs != null) {
				while (rs.next()) {

					try {

						c.setId(rs.getInt("id"));
						c.setName(rs.getString("Name"));
						c.setEmail(rs.getString("Email"));

					} catch (SQLException s) {
						s.printStackTrace();
					}

				}

				rs.close();
				return c;
			}
		} catch (SQLException s) {
			System.out.println(s);
		}

		closeConnection();
		return null;
	}

	/**
	 * Insert Contact into database
	 * 
	 * @param c Contact Object
	 * @return True if inserted
	 * @throws SQLException Any error message thrown
	 */
	public boolean insertContact(Contact c) throws SQLException {

		boolean b = false;
		try {
			String sql = "insert into contacts (Name, Email) values ('" + c.getName() + "','" + c.getEmail() + "');";
			System.out.println(sql);
			b = getConnection().execute(sql);
			closeConnection();
			b = true;
		} catch (SQLException s) {
			throw new SQLException("Contact Not Added");
		}
		return b;
	}
	
	public boolean deleteContact(String id) throws SQLException {
	boolean b = false;
	try {
		String sql = "delete from contacts where id= "+ id +";";
		System.out.println(sql);
		b = getConnection().execute(sql);
		closeConnection();
		b = true;
	} catch (SQLException s) {
		throw new SQLException("Contact Not deleted");
	}
	return b;
	}
	
//	public boolean updateContact(Contact c) throws SQLException {
//		boolean b = false;
//		// YOUR CODE HERE
//		return b;
//	}
}
