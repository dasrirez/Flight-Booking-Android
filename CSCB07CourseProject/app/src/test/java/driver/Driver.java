package driver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import flight.Flight;
import flight.Itinerary;
import managers.ClientManager;

/** A Driver used for autotesting the project backend. */
public class Driver {

  public static final double MIN_LAYOVER = 30;
  public static final double MAX_LAYOVER = 6 * 60;

  private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
  private static DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  private static Database database = new Database();
  private static ClientManager clientManager;
  private static ClientManager flightManager;

  /**
   * Uploads client information to the Database from the file at the given path.
   *
   * @param path the path to an input text file of client information with lines in the format:
   *        LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate The ExpiryDate is stored
   *        in the format yyyy-MM-dd.
   */
  public void uploadClientInfo(String path) {
    clientManager.uploadInfo(path);
  }

  /**
   * Uploads flight information to the Database from the file at the given path.
   *
   * @param path the path to an input text file of flight information with lines in the format:
   *        Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price The dates are
   *        in the format yyyy-MM-dd HH:mm; the price has exactly two decimal places.
   */
  public static void uploadFlightInfo(String path) {
    flightManager.uploadInfo(path);
  }

  /**
   * Returns the information stored for the client with the given email.
   *
   * @param email the email address of a client
   * @return the information stored for the client with the given email in this format:
   *         LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate (the ExpiryDate is stored
   *         in the format yyyy-MM-dd)
   */
  public static String getClient(String email) {
    return database.getClient(email);
  }

  /**
   * Returns all flights that depart from origin and arrive at destination on the given date.
   *
   * @param date a departure date (in the format yyyy-MM-dd)
   * @param origin a flight origin
   * @param destination a flight destination
   * @return the flights that depart from origin and arrive at destination on the given date
   *         formatted in exactly this format:
   *         Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price The dates are
   *         in the format yyyy-MM-dd HH:mm; the price has exactly two decimal places.
   * @throws ParseException if date cannot be parsed
   */
  public static List<String> getFlights(String date, String origin, String destination)
      throws ParseException {

    List<Flight> oldFlights = database.getFlights(date, origin, destination);

    // Putting all flights from old_flights into the list of String's
    List<String> flights = new ArrayList<>();
    for (Flight flight : oldFlights) {
      flights.add(flight.toString());
    }

    return flights;
  }

  /**
   * Returns all itineraries that depart from origin and arrive at destination on the given date. If
   * an itinerary contains two consecutive flights F1 and F2, then the destination of F1 should
   * match the origin of F2. To simplify our task, if there are more than MAX_LAYOVER hours or less
   * than MIN_LAYOVER between the arrival of F1 and the departure of F2, then we do not consider
   * this sequence for a possible itinerary.
   *
   * @param date a departure date (in the format yyyy-MM-dd)
   * @param origin a flight original
   * @param destination a flight destination
   * @return itineraries that depart from origin and arrive at destination on the given date with
   *         valid layover. Each itinerary in the output should contain one line per flight, in the
   *         format: Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination followed by
   *         total price (on its own line, exactly 2 decimal places), followed by total duration (on
   *         its own line, measured in hours with exactly 2 decimal places).
   */
  public static List<String> getItineraries(String date, String origin, String destination) {
    List<String> result = new ArrayList<>();
    List<Itinerary> itins = database.getItineraries(date, origin, destination);
    for (Itinerary itin : itins) {
      result.add(itin.toString());
    }
    return result;
  }

  /**
   * Returns the same itineraries as getItineraries produces, but sorted according to total
   * itinerary cost, in non-decreasing order.
   *
   * @param date a departure date (in the format yyyy-MM-dd)
   * @param origin a flight original
   * @param destination a flight destination
   * @return itineraries (sorted in non-decreasing order of total itinerary cost) in the same format
   *         as in getItineraries.
   */
  public static List<String> getItinerariesSortedByCost(String date, String origin,
      String destination) {
    List<String> result = new ArrayList<>();
    List<Itinerary> itins = database.getItinerariesByCost(date, origin, destination);
    for (Itinerary itin : itins) {
      result.add(itin.toString());
    }
    return result;
  }

  /**
   * Returns the same itineraries as getItineraries produces, but sorted according to total
   * itinerary travel time, in non-decreasing order.
   *
   * @param date a departure date (in the format yyyy-MM-dd)
   * @param origin a flight original
   * @param destination a flight destination
   * @return itineraries (sorted in non-decreasing order of total travel time) in the same format as
   *         in getItineraries.
   */
  public static List<String> getItinerariesSortedByTime(String date, String origin,
      String destination) {
    List<String> result = new ArrayList<>();
    List<Itinerary> itins = database.getItinerariesByTime(date, origin, destination);
    for (Itinerary itin : itins) {
      result.add(itin.toString());
    }
    return result;
  }
}
