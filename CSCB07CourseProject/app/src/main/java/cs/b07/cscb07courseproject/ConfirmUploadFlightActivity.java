package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import managers.FlightManager;

public class ConfirmUploadFlightActivity extends AppCompatActivity {

    private File flightsFile;
    private String filePath;
    private FlightManager flightManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload_flight);
        Intent intent = getIntent();

        flightsFile = (File) intent.getSerializableExtra(AdminPanelActivity.FLIGHT_KEY);
        filePath = flightsFile.toString();

        String content = "";
        try {
            content = flightManager.readFromFile(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TextView view = (TextView) findViewById(R.id.flight_info);
        view.setText(content);
        view.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Uploads the data
     * @param view the view
     */
    public void uploadData(View view){
        try {
            flightManager = new FlightManager(flightsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
}