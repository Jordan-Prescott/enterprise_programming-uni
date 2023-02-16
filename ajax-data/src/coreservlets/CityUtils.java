package coreservlets;

import java.util.*;

/** Static methods for retrieving information about cities.
 *  <p>
 */

public class CityUtils {
  private static Map<String,City> biggestAmericanCities = 
    new HashMap<String,City>();
  
  public static Map<String,City> getCityMap() {
    return(biggestAmericanCities);
  }
  
  private static void storeCity(String name, int timeZone, int population) {
    City city = new City(name, timeZone, population);
    name = name.toUpperCase();
    biggestAmericanCities.put(name, city);
  }
  
  public static City getCity(String name) {
    name = name.toUpperCase();
    return(biggestAmericanCities.get(name));
  }
  
  public static City getCityOrDefault(String name) {
    City city = getCity(name);
    if (city == null) {
      name = name + " (unknown: assuming east coast)";
      city = new City(name, 0, 0);
    }
    return(city);
  }
  
  private static Map<String,String[]> cityTypeMap;
  
  public static Map<String,String[]> getCityTypeMap() {
    return(cityTypeMap);
  }

  static {
    storeCity("New York", 0, 8250567);
    storeCity("Los Angeles", -3, 3849368);
    storeCity("Chicago", -1, 2873326);
    storeCity("Houston", -1, 2144491);
    storeCity("Phoenix", -2, 1512986);
    storeCity("Philadelphia", 0, 1448396);
    storeCity("San Antonio", -1, 1296682);
    storeCity("San Diego", -3, 1256951);
    storeCity("Dallas", -1, 1232940);
    storeCity("San Jose", -3, 929936);
    storeCity("Detroit", 0, 918849);
    storeCity("Jacksonville", 0, 794555);
    storeCity("Indianapolis", 0, 785597);
    storeCity("San Francisco", -3, 744041);
    storeCity("Columbus", 0, 733203);
    storeCity("Austin", -1, 709893);
    storeCity("Memphis", -1, 670902);
    storeCity("Fort Worth", -1, 653320);
    storeCity("Baltimore", 0, 640961);
    storeCity("Charlotte", 0, 630478);
    storeCity("El Paso", -2, 609415);
    storeCity("Milwaukeee", -1, 602782);
    storeCity("Boston", 0, 590763);
    storeCity("Seattle", -3, 582454);
    storeCity("Washington DC", 0, 581530);
    storeCity("Denver", -2, 566974);
    storeCity("Louisville", 0, 554496);
    storeCity("Las Vegas", -3, 552539);
    storeCity("Nashville", -1, 552120);
    storeCity("Oklahoma City", -1, 537734);
    storeCity("Miami", 0, 404048);
    cityTypeMap = makeCityMap();

  }
  
  private static Map<String,String[]> makeCityMap() {
    String[] topFiveCities =
      {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
    String[] secondFiveCities =
      {"Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"};
    String[] citiesStartingWithS =
      {"San Antonio", "San Diego", "San Jose", "San Francisco", "Seattle"};
    String[] superbowlHosts =
      {"Phoenix", "Miami", "Detroit", "Jacksonville", "Houston"};
    Map<String,String[]> cityTypeMap = new HashMap<String,String[]>();
    cityTypeMap.put("top-5-cities", topFiveCities);
    cityTypeMap.put("second-5-cities", secondFiveCities);
    cityTypeMap.put("cities-starting-with-s", citiesStartingWithS);
    cityTypeMap.put("superbowl-hosts", superbowlHosts);
    return(cityTypeMap);
  }
    
  public static List<City> findCities(String cityType) {
    String[] cityNames = cityTypeMap.get(cityType);
    if (cityNames == null) {
      String[] twoCities = { "New York", "Los Angeles" };
      cityNames = twoCities;
    }
    List<City> cities = new ArrayList<City>();
    for(String cityName: cityNames) {
      cities.add(getCity(cityName));
    }
    return(cities);
  }
  
  private CityUtils() {} // Uninstantiatable class
}
