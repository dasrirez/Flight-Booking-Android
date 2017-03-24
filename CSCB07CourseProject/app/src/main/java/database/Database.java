package database;

import flight.Flight;
import flight.Itinerary;
import user.Client;
import user.Admin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * The Database to store the users and flights.
 *
 * @author Nathan, Kareem, Arya, Daniel,
 */
public class Database implements Serializable {

    public static final double MIN_LAYOVER = 30;
    public static final double MAX_LAYOVER = 6 * 60;

    public static HashMap<String, Client> clients = new HashMap<String, Client>();
    public static HashMap<String, Admin> admins = new HashMap<String, Admin>();
    public static HashMap<String, Flight> mapFlights = new HashMap<String, Flight>();
    public static HashMap<String, String> passwords = new HashMap<>();
    public static HashMap<String, Set<Itinerary>> bookedItineraries = new HashMap<>();

    private DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Creates a new <code>Database</code>.
     */
    public Database() {
    }

    /**
     * Returns the information stored for the client with the given email.
     *
     * @param email the email address of a client
     * @return the information stored for the client with the given email in this format:
     * LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate (the ExpiryDate is stored
     * in the format yyyy-MM-dd)
     */
    public String getClient(String email) {
        String clientInfo = "";
        if ((clients.get(email)) != null) {
            Client client = clients.get(email);
            clientInfo = client.toString();
        }
        return clientInfo;
    }

    public void bookItinerary(String email, Itinerary itinerary) {
        Set<Itinerary> userItineraries = bookedItineraries.get(email);
        if (!userItineraries.contains(itinerary)) {
            userItineraries.add(itinerary);
            for (Flight flight : itinerary.getFlightPath()) {
                flight.setNumSeats(flight.getNumSeats() - 1);
            }
        }
    }

    /**
     * Returns all flights that depart from origin and arrive at destination on the given date.
     *
     * @param date        a departure date (in the format yyyy-MM-dd)
     * @param origin      a flight origin
     * @param destination a flight destination
     * @return the flights that depart from origin and arrive at destination on the given date
     * formatted in exactly this format:
     * Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price The dates are
     * in the format yyyy-MM-dd HH:mm; the price has exactly two decimal places.
     */
    public List<Flight> getFlights(String date, String origin, String destination) {
        List<Flight> flights = new ArrayList<Flight>();
        Flight flight;
        for (String flightId : mapFlights.keySet()) {
            flight = mapFlights.get(flightId);
            if (flight.getDepartureDate().equals(date) && flight.getOrigin().equals(origin)
                    && flight.getDestination().equals(destination)) {
                flights.add(flight);
            }
        }
        return flights;
    }

    /**
     * Returns all flights that depart from origin and on the given date.
     *
     * @param date   a departure date (in the format yyyy-MM-dd)
     * @param origin a flight origin
     * @return the flights that depart from origin and arrive at destination on the given date
     * formatted in exactly this format:
     * Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price The dates are
     * in the format yyyy-MM-dd HH:mm; the price has exactly two decimal places.
     * @throws ParseException if date cannot be parsed
     */
    public List<Flight> getFlights(String date, String origin) {
        List<Flight> flights = new ArrayList<Flight>();
        Flight flight;
        for (String flightId : mapFlights.keySet()) {
            flight = mapFlights.get(flightId);
            if (flight.getDepartureDate().equals(date) && flight.getOrigin().equals(origin)) {
                flights.add(flight);
            }
        }
        return flights;
    }

    /**
     * Gets a flight given a flight number
     * @param flightNum the flight number for the flight to get
     * @return a flight given a flight number
     */
    public Flight getFlight(String flightNum) {
        Flight flight = mapFlights.get(flightNum);
        return flight;
    }

    /**
     * Returns all itineraries that depart from origin and arrive at destination on the given date. If
     * an itinerary contains two consecutive flights F1 and F2, then the destination of F1 should
     * match the origin of F2. To simplify our task, if there are more than MAX_LAYOVER hours or less
     * than MIN_LAYOVER between the arrival of F1 and the departure of F2, then we do not consider
     * this sequence for a possible itinerary.
     *
     * @param date        a departure date (in the format yyyy-MM-dd)
     * @param origin      a flight original
     * @param destination a flight destination
     * @return itineraries that depart from origin and arrive at destination on the given date with
     * valid layover. Each itinerary in the output should contain one line per flight, in the
     * format: Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination followed by
     * total price (on its own line, exactly 2 decimal places), followed by total duration (on
     * its own line, measured in hours with exactly 2 decimal places).
     */
    public List<Itinerary> getItineraries(String date, String origin, String destination) {
        List<Itinerary> itineraries = new ArrayList<>();
        List<List<Flight>> flightPaths = new ArrayList<>();
        List<Flight> originFlights = getFlights(date, origin);

        // try to reach the destination from each flight departing from origin
        for (Flight originFlight : originFlights) {
            // direct flights
            if (destination.equals(originFlight.getDestination())
                    && originFlight.getNumSeats() > 0) {
                itineraries.add(new Itinerary(originFlight));
            } else {
                // lay-over flights
                if (originFlight.getNumSeats() > 0)
                    flightPaths = getFlightPaths(date, originFlight, destination);

                for (List<Flight> flightPath : flightPaths) {
                    itineraries.add(new Itinerary(flightPath));
                }
            }
        }
        return itineraries;
    }

    /**
     * Returns a list of flight paths to destination taking place on the given date, after the arrival
     * of <code>prevFlight</code>. All flights will be within the lay-over limits defined in Driver.
     *
     * @param date        the date of all flights in this flight path
     * @param prevFlight  the previous flight leading to this flight path
     * @param destination the destination the flight paths will reach
     * @return a list of list of flights, a list of flights from prevFlight to destination
     */
    private List<List<Flight>> getFlightPaths(String date, Flight prevFlight,
                                              String destination) {
        List<List<Flight>> flightPaths = new ArrayList<>();
        List<Flight> flights = getFlights(date, prevFlight.getDestination());

        long layoverMillis;
        double layoverMins;

        for (Flight flight : flights) {

            layoverMillis = flight.getDepartureDateTime().getTime()
                    - prevFlight.getArrivalDateTime().getTime();
            layoverMins = layoverMillis / (60 * 1000);

            // duration & seat check
            if (layoverMins >= MIN_LAYOVER && layoverMins <= MAX_LAYOVER
                    && flight.getNumSeats() > 0) {

                List<Flight> flightPath = new ArrayList<>();
                List<List<Flight>> layoverPaths;

                // path to destination found
                if (destination.equals(flight.getDestination())) {
                    flightPath.add(prevFlight);
                    flightPath.add(flight);
                    flightPaths.add(flightPath);
                } else {

                    // find paths from new branching flight if any exist
                    layoverPaths = getFlightPaths(date, flight, destination);

                    for (List<Flight> layoverPath : layoverPaths) {
                        // if the path is non empty then the destination has been reached in this path
                        if (!layoverPath.isEmpty()) {
                            flightPath.add(prevFlight);
                            flightPath.addAll(layoverPath);
                            flightPaths.add(flightPath);
                        }
                    }
                }
            }
        }
        return flightPaths;
    }

    /**
     * Returns the same itineraries as getItineraries produces, but sorted according to total
     * itinerary cost, in non-decreasing order.
     *
     * @param date        a departure date (in the format yyyy-MM-dd)
     * @param origin      a flight original
     * @param destination a flight destination
     * @return itineraries (sorted in non-decreasing order of total itinerary cost) in the same format
     * as in getItineraries.
     */
    public List<Itinerary> getItinerariesByCost(String date, String origin,
                                                String destination) {
        List<Itinerary> itins = getItineraries(date, origin, destination);
        // itins.sort(Itinerary.compareCost());
        Collections.sort(itins, Itinerary.compareCost());
        return itins;
    }

    /**
     * Returns the same itineraries as getItineraries produces, but sorted according to total
     * itinerary travel time, in non-decreasing order.
     *
     * @param date        a departure date (in the format yyyy-MM-dd)
     * @param origin      a flight original
     * @param destination a flight destination
     * @return itineraries (sorted in non-decreasing order of total travel time) in the same format as
     * in getItineraries.
     */
    public List<Itinerary> getItinerariesByTime(String date, String origin,
                                                String destination) {
        List<Itinerary> itins = getItineraries(date, origin, destination);
        //itins.sort(Itinerary.compareTime());
        Collections.sort(itins, Itinerary.compareTime());
        return itins;
    }

}
