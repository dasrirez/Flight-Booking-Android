package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminMainMenu extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);
        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra(MainActivity.EMAIL_KEY);

    }

    public void startFlightBooking(View view) {
        Intent intent = new Intent(this, FlightBookingActivity.class);
        intent.putExtra(MainActivity.EMAIL_KEY, email);
        startActivity(intent);
    }

    public void startAdminPanel(View view) {
        Intent intent = new Intent(this, AdminPanelActivity.class);
        startActivity(intent);
    }

    public void viewBookedItineraries(View view) {
        Intent intent = new Intent(this, ViewBookedItinerariesActivity.class);
        intent.putExtra(MainActivity.EMAIL_KEY, email);
        startActivity(intent);
    }
}
