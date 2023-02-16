package coreservlets;

/** A class that represents a banking customer. In this 
 *  particular application, there is no need for setter methods,
 *  so the fields are made final.
 */

public class Customer {
  private final String customerId, firstName, lastName;
  private final double balance;
  
  public Customer(String customerId, String firstName, 
                  String lastName, double balance) {
    this.customerId = customerId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.balance = balance;
  }
  
  public String getCustomerId() {
    return(customerId);
  }
  
  public String getFirstName() {
    return(firstName);
  }
  
  public String getLastName() {
    return(lastName);
  }
  
  public double getBalance() {
    return(balance);
  }
  
  public String getFormattedBalance() {
    return(String.format("$%,.2f", getBalance()));
  }
}
