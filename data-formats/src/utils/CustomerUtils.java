package utils;

import java.util.*;

import models.Customer;

public class CustomerUtils {
  private static Map<String,Customer> sampleCustomers;
  
  static {
    sampleCustomers = new LinkedHashMap<String,Customer>();
    sampleCustomers.put("a1234", 
      new Customer("a1234", "James", "Gosling", 12345.67));
    sampleCustomers.put("a1235", 
        new Customer("a1235", "Jesse James", "Garrett", 45678.90));
    sampleCustomers.put("a1236", 
        new Customer("a1236", "Michael", "Bloomberg", -123.45));
    sampleCustomers.put("a1237", 
        new Customer("a1237", "Michael", "Jordan", 23.23));
    sampleCustomers.put("a1238", 
        new Customer("a1238", "Jon", "Bosak", 222.22));
    sampleCustomers.put("a1239", 
        new Customer("a1239", "Eric", "Schmidt", -3333.33));
    sampleCustomers.put("a1240", 
        new Customer("a1240", "Brendan", "Eich", 152.91));
    sampleCustomers.put("a1241", 
        new Customer("a1241", "Tim", "Berners-Lee", 4.04));
  }
  
  public static Map<String,Customer> getSampleCustomers() {
    return(sampleCustomers);
  }
  
  /** Given an id, returns either the corresponding Customer
   *  object or null. Real ids are in lower case, but this
   *  accepts input in any case.
   */
  
  public static Customer getCustomer(String id) {
    if (id == null) {
      id = "unknown";
    }
    return(sampleCustomers.get(id.toLowerCase()));
  }
  
  /** Given an id, returns either the corresponding Customer
   *  object or a new Customer object with "Unknown" as the
   *  first and last name and 0 as the balance. 
   */
  
  public static Customer getCustomerOrDefault(String id) {
    Customer customer = getCustomer(id);
    String unknown = "Unknown";
    if (customer == null) {
      customer = new Customer(id, unknown, unknown, 0);
    }
    return(customer);
  }
  
  /** Given a first name and a last name, returns either
   *  the corresponding Customer object or null. Assumes
   *  that there are no two customers with the same
   *  first and last name.
   */
  
  public static Customer getNamedCustomer(String firstName,
                                          String lastName) {
    Collection<Customer> customers = getSampleCustomers().values();
    for(Customer c: customers) {
      if((c.getFirstName().equalsIgnoreCase(firstName)) &&
         (c.getLastName().equalsIgnoreCase(lastName))) {
        return(c);
      }
    }
    return(null);
  }
  
  /** Returns the customer with the highest balance. */
  
  public static Customer getRichestCustomer() {
    Map<String,Customer> customers = getSampleCustomers();
    double maxBalance = -Double.MAX_VALUE;
    Customer richestCustomer = null; 
    for(Customer c: customers.values()) {
      if (c.getBalance() > maxBalance) {
        maxBalance = c.getBalance();
        richestCustomer = c;
      }
    }
    return(richestCustomer);
  }
  
  /** Returns the TWO customers with the highest balances. */
  
  public static Customer[] getTwoRichestCustomers() {
    Map<String,Customer> customers = getSampleCustomers();
    double maxBalance = -Double.MAX_VALUE;
    Customer firstCustomer = null; 
    for(Customer c: customers.values()) {
      if (c.getBalance() > maxBalance) {
        maxBalance = c.getBalance();
        firstCustomer = c;
      }
    }
    maxBalance = -Double.MAX_VALUE;
    Customer secondCustomer = null;
    for(Customer c: customers.values()) {
      if ((c.getBalance() > maxBalance) &&
          (c.getBalance() < firstCustomer.getBalance())) {
        maxBalance = c.getBalance();
        secondCustomer = c;
      }
    }
    Customer[] twoRichest = {firstCustomer, secondCustomer};
    return(twoRichest);
  }
  
  private CustomerUtils() {} // Non-instantiatable class
}
