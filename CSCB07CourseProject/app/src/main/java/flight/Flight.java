package flight;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A representation of a Flight.
 *
 * @author Kareem
 */
public class Flight implements Serializable {

  // Creating all instance variables for the flight
  private String flightNumber;
  private Date departureDateTime;
  private Date arrivalDateTime;
  private String airline;
  private String origin;
  private String destination;
  private double price;
  private Integer numSeats;

  // Creating all necessary DateFormat variables
  private static DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");


  /**
   * Creates new <code>Flight</code> with the given flight number, departure date, arrival date,
   * airline, origin, destination and price.
   *
   * @param flightNumber the flight number of the new <code>Flight</code>
   * @param departureDateTime the departure date of the new <code>Flight</code>
   * @param arrivalDateTime the arrival date of the new <code>Flight</code>
   * @param airline the airline of the new <code>Flight</code>
   * @param origin the origin of the new <code>Flight</code>
   * @param destination the destination of the new <code>Flight</code>
   * @param price the price of the new <code>Flight</code>
   */
  public Flight(String flightNumber, Date departureDateTime, Date arrivalDateTime, String airline,
      String origin, String destination, double price, Integer numSeats) {

    this.flightNumber = flightNumber;
    this.departureDateTime = departureDateTime;
    this.arrivalDateTime = arrivalDateTime;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.price = price;
    this.numSeats = numSeats;
  }


  /**
   * Gets the flight number of this <code>Flight</code>.
   *
   * @return the flight number of this <code>Flight</code>
   */
  public String getFlightNumber() {
    return flightNumber;
  }


  /**
   * Sets a new flight number for this <code>Flight</code>.
   *
   * @param flightNumber the flightNumber to set
   */
  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }


  /**
   * Gets the departureDateTime of this <code>Flight</code>.
   *
   * @return the departureDateTime of this <code>Flight</code>
   */
  public Date getDepartureDateTime() {
    return departureDateTime;
  }


  /**
   * Sets a new departureDateTime for this <code>Flight</code>.
   *
   * @param departureDateTime the departureDateTime to set
   */
  public void setDepartureDateTime(Date departureDateTime) {
    this.departureDateTime = departureDateTime;
  }


  /**
   * Gets the departureDate in format yyyy-MM-dd of this <code>Flight</code>.
   *
   * @return the departureDateTime the String in the form of yyyy-MM-dd
   */
  public String getDepartureDate() {
    return date.format(departureDateTime);
  }


  /**
   * Gets the arrivalDateTime of this <code>Flight</code>.
   *
   * @return the arrivalDateTime of this <code>Flight</code>
   */
  public Date getArrivalDateTime() {
    return arrivalDateTime;
  }


  /**
   * Sets a new arrivalDateTime for this <code>Flight</code>.
   *
   * @param arrivalDateTime the arrivalDateTime to set
   */
  public void setArrivalDateTime(Date arrivalDateTime) {
    this.arrivalDateTime = arrivalDateTime;
  }


  /**
   * Gets the airline of this <code>Flight</code>.
   *
   * @return the airline of this <code>Flight</code>
   */
  public String getAirline() {
    return airline;
  }


  /**
   * Sets a new airline for this <code>Flight</code>.
   *
   * @param airline the airline to set
   */
  public void setAirline(String airline) {
    this.airline = airline;
  }

  /**
   * Gets the origin of this <code>Flight</code>.
   *
   * @return the origin of this <code>Flight</code>
   */
  public String getOrigin() {
    return origin;
  }


  /**
   * Sets a new origin for this <code>Flight</code>.
   *
   * @param origin the origin to set
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }


  /**
   * Gets the destination of this <code>Flight</code>.
   *
   * @return the destination of this <code>Flight</code>
   */
  public String getDestination() {
    return destination;
  }


  /**
   * Sets a new destination for this <code>Flight</code>.
   *
   * @param destination the destination to set
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }


  /**
   * Gets the price of this <code>Flight</code>.
   *
   * @return the price of this <code>Flight</code>
   */
  public double getPrice() {
    return price;
  }


  /**
   * Sets a new price for this <code>Flight</code>.
   *
   * @param price the price to set
   */
  public void setPrice(double price) {
    this.price = price;
  }


  /**
   * Gets the number of seats of this <code>Flight</code>.
   *
   * @return the number of seats of this <code>Flight</code>
   */
  public Integer getNumSeats() {
    return numSeats;
  }


  /**
   * Sets a new number of seats for this <code>Flight</code>.
   *
   * @param numSeats the number of seats to set
   */
  public void setNumSeats(Integer numSeats) {
    this.numSeats = numSeats;
  }


  @Override
  public String toString() {
    String flightNumber = getFlightNumber();
    Date departureDateTime = getDepartureDateTime();
    Date arrivalDateTime = getArrivalDateTime();
    String airline = getAirline();
    String origin = getOrigin();
    String destination = getDestination();
    double price = getPrice();
    Integer numSeats = getNumSeats();

    // Returning the string formatted version of the flight
    return String.format("%s;%s;%s;%s;%s;%s;%.2f;%d", flightNumber,
        dateTime.format(departureDateTime), dateTime.format(arrivalDateTime), airline, origin,
        destination, price, numSeats);
  }

}
