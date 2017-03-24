package cs.b07.cscb07courseproject;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.io.File;
import java.io.IOException;

import database.Database;

/**
 * Displays the admin panel
 */
public class AdminPanelActivity extends AppCompatActivity {

    public static final String FLIGHT_KEY = "flightKey";
    public static final String CLIENT_KEY = "emailKey";
    public static final Integer FLIGHT_REQUEST_CODE = 1;
    public static final Integer CLIENT_REQUEST_CODE = 0;
    private String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
    }

    /**
     * Loads client file.
     * @param view the view
     */
    public void loadClientFile(View view) {
        EditText fileName = (EditText) findViewById(R.id.client_file_name);
        String fileNameText = fileName.getText().toString();
        File userdata = this.getApplicationContext().getFilesDir();
        File clientsFile = new File(userdata, fileNameText);

        if (fileNameText.isEmpty()) {
            fileName.setError("Enter a file name!");
        } else if (!clientsFile.exists()) {
            fileName.setError("File not found!");
        }

        if (fileName.getError() == null) {
            Intent intent = new Intent(this, ConfirmUploadClientActivity.class);
            intent.putExtra(CLIENT_KEY, clientsFile);
            startActivity(intent);
        }
    }

    /**
     * Loads flight file
     * @param view the view
     */
    public void loadFlightFile(View view) {
        EditText fileName = (EditText) findViewById(R.id.flight_file_name);
        String fileNameText = fileName.getText().toString();
        File userdata = this.getApplicationContext().getFilesDir();
        File flightsFile = new File(userdata, fileNameText);

        if (fileNameText.isEmpty()) {
            fileName.setError("Enter a file name!");
        } else if (!flightsFile.exists()) {
            fileName.setError("File not found!");
        }
        if (fileName.getError() == null) {
            Intent intent = new Intent(this, ConfirmUploadFlightActivity.class);
            intent.putExtra(FLIGHT_KEY, flightsFile);
            startActivity(intent);
        }
    }

    /**
     * Opens client file.
     * @param view the view
     */
    public void openClientFile(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        startActivityForResult(intent, CLIENT_REQUEST_CODE);
    }

    /**
     * Opens flight file.
     * @param view the view
     */
    public void openFlightFile(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        startActivityForResult(intent, FLIGHT_REQUEST_CODE);
    }

    /**
     * Edits client info
     * @param view the view
     */
    public void editClientInfo(View view) {
        EditText textClientEmail = (EditText) findViewById(R.id.client_email);
        String clientEmail = textClientEmail.getText().toString();
        if (Database.clients.containsKey(clientEmail)) {
            Intent intent = new Intent(this, EditClientInfoActivity.class);
            intent.putExtra(CLIENT_KEY, clientEmail);
            startActivity(intent);
        } else {
            textClientEmail.setError("This client does not exist!");
        }

    }

    /**
     * Edits client info
     * @param view the view
     */
    public void editFlightInfo(View view) {
        EditText textFlightNumber = (EditText) findViewById(R.id.flight_number);
        String flightNumber = textFlightNumber.getText().toString();
        if (Database.mapFlights.containsKey(flightNumber)) {
            Intent intent = new Intent(this, EditFlightInfoActivity.class);
            intent.putExtra(FLIGHT_KEY, flightNumber);
            startActivity(intent);
        } else {
            textFlightNumber.setError("This flight does not exist!");
        }

    }

    /**
     * Views booked itineraries
     * @param view the view
     */
    public void viewBookedItineraries(View view) {
        EditText textClientEmail = (EditText) findViewById(R.id.view_client_itineraries);
        String clientEmail = textClientEmail.getText().toString();
        if (Database.clients.containsKey(clientEmail)) {
            Intent intent = new Intent(this, ViewBookedItinerariesActivity.class);
            intent.putExtra(CLIENT_KEY, clientEmail);
            startActivity(intent);
        } else {
            textClientEmail.setError("This client does not exist!");
        }
    }

    public void bookItinerary(View view) {
        EditText textClientEmail = (EditText) findViewById(R.id.book_itinerary_client);
        String clientEmail = textClientEmail.getText().toString();
        if (Database.clients.containsKey(clientEmail)) {
        Intent intent = new Intent(this, FlightBookingActivity.class);
        intent.putExtra(MainActivity.EMAIL_KEY, clientEmail);
        startActivity(intent);
        } else {
            textClientEmail.setError("This client does not exist!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedFileURI = data.getData();
            filePath = getRealPathFromURI(selectedFileURI);
            if (requestCode == FLIGHT_REQUEST_CODE) {
                File flightsFile = new File(filePath);
                Intent intent = new Intent(this, ConfirmUploadFlightActivity.class);
                intent.putExtra(FLIGHT_KEY, flightsFile);
                startActivity(intent);
            } else if (requestCode == CLIENT_REQUEST_CODE) {
                File clientsFile = new File(filePath);
                Intent intent = new Intent(this, ConfirmUploadClientActivity.class);
                intent.putExtra(CLIENT_KEY, clientsFile);
                startActivity(intent);
            }
        }
    }

    /**
     * Gets path from Uri
     * @param uri the Uri
     */
    public String getRealPathFromURI(Uri uri) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];
        return Environment.getExternalStorageDirectory() + "/" + split[1];

    }
}