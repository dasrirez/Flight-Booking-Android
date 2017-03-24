package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Set;

import database.Database;

public class ViewBookedItinerariesActivity extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booked_itineraries);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra(AdminPanelActivity.CLIENT_KEY);

        Set bookedItineraries = Database.bookedItineraries.get(email);

        TextView newlyRegistered = (TextView) findViewById(R.id.booked_itineraries);
        newlyRegistered.setText(bookedItineraries.toString());
    }
}
