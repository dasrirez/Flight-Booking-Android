package user;

import java.io.Serializable;
import java.util.List;

import flight.Itinerary;

/**
 * A representation of a User.
 *
 * @author Daniel
 *
 */
public abstract class User implements Serializable {
  private String lastName;
  private String firstNames;
  private String email;

  /**
   * Creates a new <code>User</code> with the given first name, last name, email
   *
   * @param lastName the last name of the new <code>User</code>
   * @param firstNames the first name(s) of the new <code>User</code>
   * @param email the email of the new <code>User</code>
   */
  public User(String lastName, String firstNames, String email) {
    this.lastName = lastName;
    this.firstNames = firstNames;
    this.email = email;
  }

  /**
   * Returns the last name of this <code>User</code>.
   *
   * @return the last name of this <code>User</code>
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name of this <code>User</code>.
   *
   * @param lastName the last name to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the first name(s) of this <code>User</code>.
   *
   * @return the first name(s) of this <code>User</code>
   */
  public String getFirstNames() {
    return firstNames;
  }

  /**
   * Sets the first name(s) of this <code>User</code>.
   *
   * @param firstNames the first name(s) to set
   */
  public void setFirstNames(String firstNames) {
    this.firstNames = firstNames;
  }

  /**
   * Returns the email of this <code>User</code>.
   *
   * @return the email of this <code>User</code>
   */
  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return String.format("%s;%s;%s", lastName, firstNames, email);
  }


}
