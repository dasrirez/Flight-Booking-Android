package managers;

import user.Client;
import database.Database;
import flight.Itinerary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;


/**
 * Created by natha on 11/26/2016.
 */

public class ClientManager extends Manager implements Serializable {

  private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Creates a new ClientManager for the Clients whose information is stored in file filePath.
   *
   * @throws IOException if filePath cannot be opened/created.
   */
  public ClientManager(File file) throws IOException {
    super(file);
  }

  @Override
  public String toString() {
    String content = "";
    for (Client client : Database.clients.values()) {
      content += client.toString() + "\n";
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
      String clientInfo = scanner.nextLine();
      content += clientInfo + "\n";
    }
    scanner.close();
    return content;
  }

  /**
   * Uploads client information to the application from the file at the given path.
   *
   * @param path the path to an input text file of client information with lines in the format:
   *        LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate The ExpiryDate is stored
   *        in the format yyyy-MM-dd.
   */
  public void uploadInfo(String path) {
    try (Scanner sc = new Scanner(new File(path))) {
      while (sc.hasNextLine()) {
        String nextLine = sc.nextLine();
        String[] clientInfo = nextLine.split(";");
        String lastName = clientInfo[0];
        String firstNames = clientInfo[1];
        String email = clientInfo[2];
        String address = clientInfo[3];
        Long creditNum = Long.parseLong(clientInfo[4]);
        try {
          Date expiryDate = date.parse(clientInfo[5]);
          Client client = new Client(lastName, firstNames, email, address, creditNum, expiryDate);
          Database.clients.put(email, client);
          Database.bookedItineraries.put(email, new HashSet<Itinerary>());
        } catch (ParseException e1) {
          // does not add current user to database
          // and continues to read the next line for the next user
        }
      }
    } catch (FileNotFoundException e1) {
      return;
    }

  }
}
