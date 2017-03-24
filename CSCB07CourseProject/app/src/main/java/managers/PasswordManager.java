package managers;

import database.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;


/**
 * Created by natha on 11/26/2016.
 */


public class PasswordManager extends Manager implements Serializable {

  /**
   * Creates a new PasswordManager for the passwords whose information is stored in file filePath.
   *
   * @throws IOException if filePath cannot be opened/created.
   */
  public PasswordManager(File file) throws IOException {
    super(file);
  }

  @Override
  public String toString() {
    String content = "";
    for (String email : Database.passwords.keySet()) {
      content += email + ";" + Database.passwords.get(email) + "\n";
    }
    return content;
  }

  /**
   * Uploads password information to the application from the file at the given path.
   *
   * @param path the path to an input text file of password information with lines in the format:
   *        Email;Password;isAdmin
   */

  public void uploadInfo(String path) {
    try (Scanner sc = new Scanner(new File(path))) {
      while (sc.hasNextLine()) {
        String nextLine = sc.nextLine();
        String[] info = nextLine.split(";");
        String email = info[0];
        String password = info[1];
        Database.passwords.put(email, password);
      }
    } catch (FileNotFoundException e1) {
      return;
    }
  }
}
