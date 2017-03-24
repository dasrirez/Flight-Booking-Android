package managers;

import database.Database;
import flight.Flight;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/**
 * Created by natha on 11/26/2016.
 */

public class FlightManager extends Manager implements Serializable {

  private static DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  /**
   * Creates a new FlightManager for the Flights whose information is stored in file filePath.
   *
   * @throws IOException if filePath cannot be opened/created.
   */
  public FlightManager(File file) throws IOException {
    super(file);
  }

  @Override
  public String toString() {
    String content = "";
    for (Flight flight : Database.mapFlights.values()) {
      content += flight.toString() + "\n";
    }
    return content;
  }

  /**
   * Reads the file and returns a string representation of it's contents.
   *
   * @param path the filepath of the data file
   * @throws FileNotFoundException if the file path does not exist
   */
  public static String readFromFile(String path) throws FileNotFoundException {
    Scanner scanner = new Scanner(new FileInputStream(path));
    String content = "";
    while (scanner.hasNextLine()) {
      String flightInfo = scanner.nextLine();
      content += flightInfo + "\n";
    }
    scanner.close();
    return content;
  }

  /**
   * Uploads flight information to the application from the file at the given path.
   *
   * @param path the path to an input text file of flight information with lines in the format:
   *        Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price The dates are
   *        in the format yyyy-MM-dd HH:mm; the price has exactly two decimal places.
   */
  public void uploadInfo(String path) {
    try (Scanner scanner = new Scanner(new File(path))) {
      while (scanner.hasNextLine()) {
        String nextLine = scanner.nextLine();
        String[] flightInfo = nextLine.split(";");
        String flightNumber = flightInfo[0];
        Date departureDateTime = null;
        Date arrivalDateTime = null;

        try {
          departureDateTime = dateTime.parse(flightInfo[1]);
          arrivalDateTime = dateTime.parse(flightInfo[2]);
        } catch (ParseException ex) {
          return;
        }

        String airline = flightInfo[3];
        String origin = flightInfo[4];
        String destination = flightInfo[5];
        double price = Double.parseDouble(flightInfo[6]);
        Integer numSeats = Integer.parseInt(flightInfo[7]);

        Flight flight = new Flight(flightNumber, departureDateTime, arrivalDateTime, airline,
            origin, destination, price, numSeats);
        Database.mapFlights.put(flightNumber, flight);

      }
    } catch (FileNotFoundException ex) {
      return;
    }
  }
}
