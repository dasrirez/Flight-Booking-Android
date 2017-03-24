package managers;

import android.text.TextUtils;

import database.Database;
import flight.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class BookedItinerariesManager extends Manager implements Serializable {

  public BookedItinerariesManager(File file) throws IOException {
    super(file);
  }

  /**
   * Uploads information from file at path to database
   * @param path
   */
  public void uploadInfo(String path) {
    Scanner sc = null;
    try { sc = new Scanner(new File(path)); }
    catch (IOException e) { e.printStackTrace(); }

    List<Itinerary> itineraries = new ArrayList<>();
    List<Flight> flightPath;
    String[] line;
    String itineraryStrings;
    String user;

    while (sc.hasNextLine()) {
      line = sc.nextLine().split(";");
      if (!(line.length > 1))
        continue;
      user = line[0];
      itineraryStrings = line[1];
      for (String itineraryString : itineraryStrings.split(",")) {
        flightPath = new ArrayList<>();
        for (String flightNum : itineraryString.split("-")) {
          flightPath.add(Database.mapFlights.get(flightNum));
        }
        itineraries.add(new Itinerary(flightPath));
      }
      Database.bookedItineraries.get(user).addAll(itineraries);
    }
  }

  @Override
  public String toString() {
    String result = "";
    String itineraries;
    Set<Itinerary> bookedItineraries;
    for (String email : Database.bookedItineraries.keySet()) {
      itineraries = "";
      bookedItineraries = Database.bookedItineraries.get(email);

      for (Itinerary bookedItinerary : bookedItineraries) {
        itineraries += TextUtils.join("-", bookedItinerary.getFlightPathString());
        itineraries += ",";
      }
      // extra comma
      if (!itineraries.isEmpty())
        itineraries = itineraries.substring(0, itineraries.length() - 1);
      result += String.format("%s;%s\n", email, itineraries);
    }
    // extra newline
    if (!result.isEmpty())
      result = result.substring(0, result.length() - 1);
    return result;
  }
}
