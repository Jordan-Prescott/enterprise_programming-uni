package coreservlets;

import java.util.*;

public class City {
  private String name;
  private int timeZone;
  private int pop;

  public City(String name, int timeZone, int pop) {
    setName(name);
    setTimeZone(timeZone);
    setPop(pop);
  }
  
  public Calendar getTimeDate() {
    Calendar time = new GregorianCalendar();
    time.add(Calendar.HOUR_OF_DAY, getTimeZone());
    return(time);
  }
  
  public String getShortTime() {
    Calendar time = getTimeDate();
    return(String.format("%tr", time));
  }
  
  public String getLongTime() {
    Calendar time = getTimeDate();
    return(String.format("In %s, it is now %tr on %tD.",
                         getName(), time, time));
  }
  
  public String getName() {
    return (name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTimeZone() {
    return (timeZone);
  }

  public void setTimeZone(int timeZone) {
    this.timeZone = timeZone;
  }
 
  public int getPop() {
    return (pop);
  }

  public void setPop(int pop) {
    this.pop = pop;
  }
  
  public String getPopulation() {
    return(String.format("%,9d", getPop()));
  }
}
