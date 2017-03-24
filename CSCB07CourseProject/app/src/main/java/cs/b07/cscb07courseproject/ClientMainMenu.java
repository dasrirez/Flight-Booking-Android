package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ClientMainMenu extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main_menu);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra(MainActivity.EMAIL_KEY);
    }

    /**
     * Starts the flight booking layout.
     * @param view the view
     */
    public void startFlightBooking(View view){
        Intent intent = new Intent(this, FlightBookingActivity.class);
        intent.putExtra(MainActivity.EMAIL_KEY, email);
        startActivity(intent);
    }

    /**
     * Starts the edit client layout.
     * @param view the view
     */
    public void startEditClient(View view){
        Intent intent = new Intent(this, EditClientInfoActivity.class);
        intent.putExtra(MainActivity.EMAIL_KEY, email);
        startActivity(intent);
    }

    /**
     * Starts the view booked itineraries layout.
     * @param view the view
     */
    public void viewBookedItineraries(View view){
        Intent intent = new Intent(this, ViewBookedItinerariesActivity.class);
        intent.putExtra(MainActivity.EMAIL_KEY, email);
        startActivity(intent);
    }
}
