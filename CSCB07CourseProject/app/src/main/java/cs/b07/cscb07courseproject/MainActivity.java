package cs.b07.cscb07courseproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import database.Database;
import managers.AdminManager;
import managers.BookedItinerariesManager;
import managers.ClientManager;
import managers.FlightManager;
import managers.PasswordManager;

public class MainActivity extends AppCompatActivity {

    /* file names */
    private static final String USER_DATA_DIR = "records_data";
    private static final String CLIENTS_FILE = "clients.txt";
    private static final String ADMINS_FILE = "admins.txt";
    private static final String PASSWORDS_FILE = "passwords.txt";
    private static final String FLIGHTS_FILE = "flights.txt";
    private static final String BOOKED_ITINERARIES_FILE = "booked_itineraries.txt";

    /* managers */
    private FlightManager flightManager;
    private ClientManager clientManager;
    private AdminManager adminManager;
    private PasswordManager passwordManager;
    private BookedItinerariesManager bookedItinerariesManager;

    /* keys */
    public static final String EMAIL_KEY = "emailKey";

    /* screen elements */
    private EditText emailText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getApplicationContext().getFilesDir();

        // check app permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Allow Storage Permissions!", Toast.LENGTH_LONG);
                toast.show();
        }

        // fetch screen elements
        emailText = (EditText) findViewById(R.id.email_field);
        passwordText = (EditText) findViewById(R.id.password_field);

        // restart database
        Database.clients.clear();
        Database.mapFlights.clear();
        Database.admins.clear();
        Database.passwords.clear();
        Database.bookedItineraries.clear();

        // create new directories for internal storage
        File appdata = this.getApplicationContext().getDir(USER_DATA_DIR, MODE_PRIVATE);
        File clientsFile = new File(appdata, CLIENTS_FILE);
        File flightsFile = new File(appdata, FLIGHTS_FILE);
        File adminsFile = new File(appdata, ADMINS_FILE);
        File passwordFile = new File(appdata, PASSWORDS_FILE);
        File bookedItinerariesFile = new File(appdata, BOOKED_ITINERARIES_FILE);

        try {
            flightManager = new FlightManager(flightsFile);
            clientManager = new ClientManager(clientsFile);
            adminManager = new AdminManager(adminsFile);
            passwordManager = new PasswordManager(passwordFile);
            bookedItinerariesManager = new BookedItinerariesManager(bookedItinerariesFile);

            flightManager.uploadInfo("flights");
            showDatabaseLoaded(flightsFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // clear fields when previous user finishes with their layouts
        emailText.setText("");
        passwordText.setText("");
    }

    /**
     * Shows that the database has been loaded from file
     * @param file the file
     */
    public void showDatabaseLoaded(File file) {
        Context context = getApplicationContext();
        Date lastModified = new Date(file.lastModified());
        String text = "Database: " + lastModified.toString();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Transitions into sign up layout.
     * @param view the view
     */
    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Transitions into the login layout.
     * @param view the view
     */
    public void logIn(View view) {
        // fetch the inputted email and password
        String email = emailText.getText().toString();
        String passwordInput = passwordText.getText().toString();

        // prompt user to set password if they do not exist in the password database
        // otherwise bring up the appropriate layout based on their account type
        String password = Database.passwords.get(email);
        if (Database.admins.containsKey(email) && passwordInput.equals(password)) {
            Intent intent = new Intent(this, AdminMainMenu.class);
            intent.putExtra(EMAIL_KEY, email);
            startActivity(intent);
        } else if (Database.admins.containsKey(email) && !Database.passwords.containsKey(email)) {
            Intent intent = new Intent(this, CreatePasswordActivity.class);
            intent.putExtra(EMAIL_KEY, email);
            startActivity(intent);
        } else if (Database.clients.containsKey(email) && passwordInput.equals(password)) {
            Intent intent = new Intent(this, ClientMainMenu.class);
            intent.putExtra(EMAIL_KEY, email);
            startActivity(intent);
        } else if (Database.clients.containsKey(email) && !Database.passwords.containsKey(email)) {
            Intent intent = new Intent(this, CreatePasswordActivity.class);
            intent.putExtra(EMAIL_KEY, email);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, MasterInputActivity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // write the databases into their corresponding files
        flightManager.writeOutToFile();
        clientManager.writeOutToFile();
        adminManager.writeOutToFile();
        passwordManager.writeOutToFile();
        bookedItinerariesManager.writeOutToFile();
    }
}