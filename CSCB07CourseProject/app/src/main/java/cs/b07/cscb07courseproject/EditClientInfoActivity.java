package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.Database;
import user.Client;

public class EditClientInfoActivity extends AppCompatActivity {
    private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private String email;

    private EditText firstNamesText;
    private EditText lastNameText;
    private EditText passwordText;
    private EditText addressText;
    private EditText creditCardNumberText;
    private EditText expiryDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client_info);
        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra(AdminPanelActivity.CLIENT_KEY);

        Client client = Database.clients.get(email);

        firstNamesText = (EditText) findViewById(R.id.first_names_field);
        firstNamesText.setHint(client.getFirstNames());
        lastNameText = (EditText) findViewById(R.id.last_name_field);
        lastNameText.setHint(client.getLastName());
        passwordText = (EditText) findViewById(R.id.password_sign_up_field);
        passwordText.setHint(Database.passwords.get(email));
        addressText = (EditText) findViewById(R.id.address_field);
        addressText.setHint(client.getAddress());
        creditCardNumberText = (EditText) findViewById(R.id.credit_card_number_field);
        creditCardNumberText.setHint(client.getCreditNum().toString());
        expiryDateText = (EditText) findViewById(R.id.expiry_date_field);
        expiryDateText.setHint(date.format(client.getExpiryDate()));
    }

    /**
     * Updates the client information
     * @param view the view
     */
    public void updateClientInfo(View view) {
        String firstNames = firstNamesText.getText().toString();
        if(firstNames.isEmpty()) {
            firstNames = firstNamesText.getHint().toString();
        }
        String lastName = lastNameText.getText().toString();
        if(lastName.isEmpty()) {
            lastName = lastNameText.getHint().toString();
        }
        String password = passwordText.getText().toString();
        if(password.isEmpty()) {
            password = passwordText.getHint().toString();
        }
        String address = addressText.getText().toString();
        if(address.isEmpty()) {
            address = addressText.getHint().toString();
        }
        String creditCardNumberString = creditCardNumberText.getText().toString();
        if(creditCardNumberString.isEmpty()) {
            creditCardNumberString = creditCardNumberText.getHint().toString();
        }
        String expiryDateString = expiryDateText.getText().toString();
        if(expiryDateString.isEmpty()) {
            expiryDateString = expiryDateText.getHint().toString();
        }

        Client client = null;
        try {
            Long creditCardNumber = Long.parseLong(creditCardNumberString);
            Date expiryDate = date.parse(expiryDateString);
            client = new Client(lastName, firstNames, email, address, creditCardNumber, expiryDate);
            Database.clients.put(email, client);
            Database.passwords.put(email, password);

            Toast toast = Toast.makeText(getApplicationContext(), "Client Updated!",
                    Toast.LENGTH_LONG);
            toast.show();

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
