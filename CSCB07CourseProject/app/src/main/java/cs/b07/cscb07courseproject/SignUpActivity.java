package cs.b07.cscb07courseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import user.Client;
import database.Database;
import flight.Itinerary;

public class SignUpActivity extends AppCompatActivity {
    private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /**
     * Transitions into the confirm info layout.
     * @param view the view
     */
    public void confirmInfo (View view) {
        EditText firstNamesText = (EditText) findViewById(R.id.first_names_field);
        String firstNames = firstNamesText.getText().toString();
        if (firstNames.isEmpty()) {
            firstNamesText.setError("First Names cannot be left empty!");
            return;
        }

        EditText lastNameText = (EditText) findViewById(R.id.last_name_field);
        String lastName = lastNameText.getText().toString();
        if (lastName.isEmpty()) {
            lastNameText.setError("Last Name cannot be left empty!");
            return;
        }

        EditText emailText = (EditText) findViewById(R.id.email_sign_up_field);
        String email = emailText.getText().toString();
        if (email.isEmpty()) {
            emailText.setError("Email cannot be left empty!");
            return;
        }

        EditText passwordText = (EditText) findViewById(R.id.password_sign_up_field);
        String password = passwordText.getText().toString();
        if (password.isEmpty()) {
            passwordText.setError("Password cannot be left empty!");
            return;
        }

        EditText addressText = (EditText) findViewById(R.id.address_field);
        String address = addressText.getText().toString();
        if (address.isEmpty()) {
            addressText.setError("Address cannot be left empty!");
            return;
        }

        EditText creditCardNumberText = (EditText) findViewById(R.id.credit_card_number_field);
        String creditCardNumberString = creditCardNumberText.getText().toString();
        if (creditCardNumberString.isEmpty()) {
            creditCardNumberText.setError("Credit Card Number cannot be left empty!");
            return;
        }

        EditText expiryDateText = (EditText) findViewById(R.id.expiry_date_field);
        String expiryDateString = expiryDateText.getText().toString();
        if (expiryDateString.isEmpty()) {
            expiryDateText.setError("Expiry Date cannot be left empty!");
            return;
        }

        Client client = null;
        try {
            Long creditCardNumber = Long.parseLong(creditCardNumberString);
            Date expiryDate = date.parse(expiryDateString);
            client = new Client(lastName, firstNames, email, address, creditCardNumber, expiryDate);
            Database.clients.put(email, client);
            Database.passwords.put(email, password);
            Database.bookedItineraries.put(email, new HashSet<Itinerary>());
            finish();
        } catch (NumberFormatException e1) {
            creditCardNumberText.setError("Incorrect Format!");
            return;
        } catch (ParseException e1) {
            expiryDateText.setError("Incorrect Format!");
            return;
        }
    }
}