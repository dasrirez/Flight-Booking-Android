package user;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A representation of a Client.
 *
 * @author Daniel
 *
 */

public class Client extends User implements Serializable {
  private String address;
  private Long creditNum;
  private Date expiryDate;
  private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Creates a new <code>Client</code> with the given first name, last name, email, address, credit
   * card number and expiry date.
   *
   * @param lastName the last name of the new <code>Client</code>
   * @param firstNames the first name(s) of the new <code>Client</code>
   * @param email the email of the new <code>Client</code>
   * @param address the address of the new <code>Client</code>
   * @param creditNum the credit card number of the new <code>Client</code>
   * @param expiryDate the expiry date of the credit card of the new <code>Client</code>
   */
  public Client(String lastName, String firstNames, String email, String address, Long creditNum,
      Date expiryDate) {
    super(lastName, firstNames, email);
    this.address = address;
    this.creditNum = creditNum;
    this.expiryDate = expiryDate;
  }

  /**
   * Returns the address of this <code>Client</code>.
   *
   * @return the address of this <code>Client</code>
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the address of this <code>Client</code>.
   *
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Returns the credit card number of this <code>Client</code>.
   *
   * @return the credit card number of this <code>Client</code>
   */
  public Long getCreditNum() {
    return creditNum;
  }

  /**
   * Sets the credit card number of this <code>Client</code>.
   *
   * @param creditNum the credit card number to set
   */
  public void setCreditNum(Long creditNum) {
    this.creditNum = creditNum;
  }

  /**
   * Returns the expiry date of this <code>Client</code>'s credit card.
   *
   * @return the expiry date of this <code>Client</code>'s credit card
   */
  public Date getExpiryDate() {
    return expiryDate;
  }

  /**
   * Sets the credit card expiry date of this <code>Client</code>.
   *
   * @param expiryDate the expiry date to set
   */
  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  @Override
  public String toString() {
    return String.format("%s;%s;%s;%s", super.toString(), address, creditNum,
        date.format(expiryDate));
  }

}
