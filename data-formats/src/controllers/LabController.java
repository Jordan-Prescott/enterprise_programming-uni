package controllers;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import models.City;
import models.Contact;
import models.ContactList;
import models.Customer;
import models.Joke;
import utils.CityUtils;
import utils.ContactUtils;
import utils.CustomerUtils;
import utils.GeneralUtils;

public class LabController {
	

    private static String readFileAsString(String filePath) {
        // implementation of reading file into String goes here
        return null;
    }

	public static void main(String[] args) throws JAXBException {
		
		// task 1
		System.out.println("\n---------------------- Task 1"); // JAVA OBJECT TO XML
		Contact c = new Contact(7, "Cristiano Ronaldo", "goat@mail.com");
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Contact.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(c, System.out);
		
		// task 2
		System.out.println("\n---------------------- Task 2"); //JAVA ARRAY TO XML
		ArrayList<Contact> allContacts = ContactUtils.getAllContacts();
		ContactList cl = new ContactList(allContacts);
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(ContactList.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		m.marshal(cl, sw);
		System.out.println(sw.toString());
		
		// task 3
		System.out.println("\n---------------------- Task 3"); //XML TO JAVA OBJECT
		String xml = GeneralUtils.readTxtFile("contact.xml");
		JAXBContext jaxbContext2 = JAXBContext.newInstance(Contact.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Contact c2 = (Contact) jaxbUnmarshaller.unmarshal(new StringReader(xml));
		System.out.println(c.getName());
		System.out.println(c.getEmail());
		
		// task 4
		System.out.println("\n---------------------- Task 4"); // JAVA OBJECT TO JSON
		Gson gson = new Gson();
		String json = gson.toJson(c);
		System.out.println(json);
		
		// task 5
		System.out.println("\n---------------------- Task 5"); // JAVA ARRAY TO JSON
		Customer[] twoRichest = CustomerUtils.getTwoRichestCustomers();	
		String json2 = gson.toJson(twoRichest);
		System.out.println(json2);
		
		// task 6
		System.out.println("\n---------------------- Task 6"); // LIST/ARRAYLIST TO JSON
		ArrayList<City> cities = (ArrayList<City>) CityUtils.findCities("top-5-cities");
		String json3 = gson.toJson(cities);
		System.out.println(json3);
		
		// task 7
		System.out.println("\n---------------------- Task 7"); // JSON DATA TO JAVA OBJECT
		String jokesJson = GeneralUtils.readTxtFile("joke.json");
		Joke j = gson.fromJson(jokesJson, Joke.class);
		
		System.out.println(j.getSetup());
		System.out.println(j.getPunchline());
		
		// task 8
		System.out.println("\n---------------------- Task 8"); // JSON INTO ARRAY
		
		String jokesJson1 = GeneralUtils.readTxtFile("jokes.json");
		System.out.println(jokesJson1);
		ArrayList<Joke> jokes = gson.fromJson(jokesJson1, new TypeToken<ArrayList<Joke>>(){}.getType());
		
		
		for (Joke j1 : jokes) {
			System.out.println(j1.getSetup());
			System.out.println(j1.getPunchline());
			System.out.println("________________");
		}
	}
}

