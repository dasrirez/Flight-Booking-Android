package managers;

import database.Database;
import flight.Itinerary;
import user.Admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


/**
 * Created by natha on 11/26/2016.
 */

public class AdminManager extends Manager implements Serializable {

  /**
   * Creates a new AdminManager for the Admins whose information is stored in file filePath.
   *
   * @throws IOException if filePath cannot be opened/created.
   */
  public AdminManager(File file) throws IOException {
    super(file);
  }


  @Override
  public String toString() {
    String content = "";
    for (Admin admin : Database.admins.values()) {
      content += admin.toString() + "\n";
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
   * Uploads admin information to the application from the file at the given path.
   *
   * @param path the path to an input text file of admin information with lines in the format:
   *        LastName;FirstNames;Email
   */

  public void uploadInfo(String path) {
    try (Scanner sc = new Scanner(new File(path))) {
      while (sc.hasNextLine()) {
        String nextLine = sc.nextLine();
        String[] adminInfo = nextLine.split(";");
        String lastName = adminInfo[0];
        String firstNames = adminInfo[1];
        String email = adminInfo[2];
        Admin admin = new Admin(lastName, firstNames, email);
        Database.admins.put(email, admin);
        Database.bookedItineraries.put(email, new HashSet<Itinerary>());
      }
    } catch (FileNotFoundException e1) {
      return;
    }
  }
}
