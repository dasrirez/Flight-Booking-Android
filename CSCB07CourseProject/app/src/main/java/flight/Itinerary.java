package flight;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Class representing an <code>Itinerary</code>.
 *
 * @author arya
 *
 */
public class Itinerary {
  private double totalCost;
  private long travelTime;
  private List<Flight> flightPath;

  /**
   * Creates a new <code>Itinerary</code> containing the given flight path.
   *
   * @param flightPath the flight path of this <code>Itinerary</code>
   */
  public Itinerary(List<Flight> flightPath) {
    this.flightPath = flightPath;
    buildInfo();
  }

  /**
   * Creates a new <code>Itinerary</code> containing a direct flight.
   *
   * @param flight the direct flight of this <code>Itinerary</code>
   */
  public Itinerary(Flight flight) {
    flightPath = new ArrayList<>();
    flightPath.add(flight);
    buildInfo();
  }

  /**
   * Produces the fields totalCost and travelTime based on the flight path.
   */
  private void buildInfo() {
    totalCost = 0;
    travelTime = 0;

    if (!flightPath.isEmpty()) {
      // find the difference between the ending and starting flight to get total time
      Date startTime = flightPath.get(0).getDepartureDateTime();
      Date endTime = flightPath.get(flightPath.size() - 1).getArrivalDateTime();
      travelTime += endTime.getTime() - startTime.getTime();
      // build the price
      for (Flight flight : flightPath) {
        totalCost += flight.getPrice();
      }
    }
  }

  /**
   * Returns the total travel time for this <code>Itinerary</code> in mins.
   *
   * @return the total travel time for this <code>Itinerary</code> in mins
   */
  public double getTravelTimeMinutes() {
    return travelTime / (60 * 1000);
  }

  /**
   * Returns the total travel time for this <code>Itinerary</code> in hours.
   *
   * @return the total travel time for this <code>Itinerary</code> in hours
   */
  public double getTravelTimeHours() {
    return getTravelTimeMinutes() / 60.0;
  }

  /**
   * Returns the total price time for this <code>Itinerary</code>.
   *
   * @return the total price time for this <code>Itinerary</code>
   */
  public double getTotalCost() {
    return totalCost;
  }

  /**
   * Returns the flight path for this <code>Itinerary</code> in string format. Each flight is
   * represented by flight number.
   *
   * @return the flight path for this <code>Itinerary</code> in string format
   */
  public List<String> getFlightPathString() {
    List<String> result = new ArrayList<>();
    for (Flight flight : flightPath) {
      result.add(flight.getFlightNumber());
    }
    return result;
  }

  /**
   * Returns the flight path for this <code>Itinerary</code>.
   *
   * @return the flight path for this <code>Itinerary</code>
   */
  public List<Flight> getFlightPath() {
    return this.flightPath;
  }

  /**
   * Returns method comparing the itineraries based on time.
   *
   * @return method comparing the itineraries based on time
   */
  public static Comparator<Itinerary> compareTime() {
    return new Comparator<Itinerary>() {
      @Override
      public int compare(Itinerary o1, Itinerary o2) {
        return Double.compare(o1.getTravelTimeMinutes(), o2.getTravelTimeMinutes());
      }
    };
  }

  /**
   * Returns method comparing the itineraries based on price.
   *
   * @return method comparing the itineraries based on price
   */
  public static Comparator<Itinerary> compareCost() {
    return new Comparator<Itinerary>() {
      @Override
      public int compare(Itinerary o1, Itinerary o2) {
        return Double.compare(o1.getTotalCost(), o2.getTotalCost());
      }
    };
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String result = "";
    String[] flightInfo;
    String flightString;

    // format each flight, the last index containing price is ignored
    for (Flight flight : flightPath) {
      flightInfo = flight.toString().split(";");
      flightString = String.format("%s;%s;%s;%s;%s;%s", flightInfo) + ";" + flightInfo[7];

      result += flightString + "\n";
    }

    // slap on the cost and price
    result += String.format("%.2f\n%.2f", getTotalCost(), getTravelTimeHours());

    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((flightPath == null) ? 0 : flightPath.hashCode());
    long temp;
    temp = Double.doubleToLongBits(totalCost);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + (int) (travelTime ^ (travelTime >>> 32));
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Itinerary other = (Itinerary) obj;
    if (flightPath == null) {
      if (other.flightPath != null) {
        return false;
      }
    } else if (!flightPath.equals(other.flightPath)) {
      return false;
    }
    if (Double.doubleToLongBits(totalCost) != Double.doubleToLongBits(other.totalCost)) {
      return false;
    }
    if (travelTime != other.travelTime) {
      return false;
    }
    return true;
  }

}
