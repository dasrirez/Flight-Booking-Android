package cs.b07.cscb07courseproject;

import database.Database;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;


public class MasterInputActivity extends AppCompatActivity {

    public static final String ADMIN_KEY = "adminKey";
    public static final Integer ADMIN_REQUEST_CODE = 3;
    private String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_input);
    }

    /**
     * Loads the administrator file (I spelt it out for you, Brian).
     * @param view the file
     */
    public void loadAdminFile(View view) {
        EditText fileName = (EditText) findViewById(R.id.admin_file_name);
        String fileNameText = fileName.getText().toString();
        File userdata = this.getApplicationContext().getFilesDir();
        File adminsFile = new File(userdata, fileNameText);

        if (fileNameText.isEmpty()) {
            fileName.setError("Enter a file name!");
        } else if(!adminsFile.exists()) {
            fileName.setError("File not found!");
        }

        if (fileName.getError() == null) {
            Intent intent = new Intent(this, ConfirmUploadAdminActivity.class);
            intent.putExtra(ADMIN_KEY, adminsFile);
            startActivity(intent);
        }
    }

    /**
     * Opens the admin file.
     * @param view the view
     */
    public void openAdminFile(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        startActivityForResult(intent, ADMIN_REQUEST_CODE);
    }

    /**
     * Clears clients.
     * @param view the view
     */
    public void clearClients(View view){
        for (String client : Database.clients.keySet()) {
            Database.bookedItineraries.remove(client);
        }
        Database.clients.clear();
        Toast toast = Toast.makeText(getApplicationContext(), "Clients Cleared!", Toast.LENGTH_LONG);
        toast.show();
    }

     /**
     * Clears flights.
     * @param view the view
     */
    public void clearFlights(View view){
        Database.mapFlights.clear();
        clearBookedItineraries(view);
        Toast toast = Toast.makeText(getApplicationContext(), "Flights Cleared!", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Clears admins.
     * @param view the view
     */
    public void clearAdmins(View view){
        for (String admin : Database.admins.keySet()) {
            Database.bookedItineraries.remove(admin);
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Admins Cleared!", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Clears passwords.
     * @param view the view
     */
    public void clearPasswords(View view){
        Database.passwords.clear();
        Toast toast = Toast.makeText(getApplicationContext(), "Passwords Cleared!", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Clears booked itineraries.
     * @param view the view
     */
    public void clearBookedItineraries(View view){
        for (String user : Database.bookedItineraries.keySet()) {
            Database.bookedItineraries.get(user).clear();
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Booked Itineraries Cleared!", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedFileURI = data.getData();
            filePath = getRealPathFromURI(selectedFileURI);
            if (requestCode == ADMIN_REQUEST_CODE) {
                File adminsFile = new File(filePath);
                Intent intent = new Intent(this, ConfirmUploadAdminActivity.class);
                intent.putExtra(ADMIN_KEY, adminsFile);
                startActivity(intent);
            }
        }
    }

    /**
     * Gets real path from uri
     * @param uri the uri
     */
    public String getRealPathFromURI(Uri uri) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        return Environment.getExternalStorageDirectory() + "/" + split[1];
    }
}
