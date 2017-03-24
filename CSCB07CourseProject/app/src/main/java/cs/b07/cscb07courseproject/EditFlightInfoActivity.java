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
import flight.Flight;

public class EditFlightInfoActivity extends AppCompatActivity {
    private static DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String flightNumber;

    private EditText departureDateText;
    private EditText arrivalDateText;
    private EditText airlineText;
    private EditText originText;
    private EditText destinationText;
    private EditText priceText;
    private EditText numSeatsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight_info);
        Intent intent = getIntent();
        flightNumber = (String) intent.getSerializableExtra(AdminPanelActivity.FLIGHT_KEY);

        Flight flight = Database.mapFlights.get(flightNumber);

        departureDateText = (EditText) findViewById(R.id.departure_date);
        departureDateText.setHint(dateTime.format(flight.getDepartureDateTime()));
        arrivalDateText = (EditText) findViewById(R.id.arrival_date);
        arrivalDateText.setHint(dateTime.format(flight.getArrivalDateTime()));
        airlineText = (EditText) findViewById(R.id.airline);
        airlineText.setHint(flight.getAirline());
        originText = (EditText) findViewById(R.id.origin);
        originText.setHint(flight.getOrigin());
        destinationText = (EditText) findViewById(R.id.destination);
        destinationText.setHint(flight.getDestination());
        priceText = (EditText) findViewById(R.id.price);
        priceText.setHint(Double.toString(flight.getPrice()));
        numSeatsText = (EditText) findViewById(R.id.numSeats);
        numSeatsText.setHint(Integer.toString(flight.getNumSeats()));
    }

    /**
     * Updates flight information.
     * @param view the view
     */
    public void updateFlightInfo(View view) {

        String departureDateString = departureDateText.getText().toString();
        if (departureDateString.isEmpty()) {
            departureDateString = departureDateText.getHint().toString();
        }

        String arrivalDateString = arrivalDateText.getText().toString();
        if (arrivalDateString.isEmpty()) {
            arrivalDateString = arrivalDateText.getHint().toString();
        }

        String airline = airlineText.getText().toString();
        if (airline.isEmpty()) {
            airline = airlineText.getHint().toString();
        }

        String origin = originText.getText().toString();
        if (origin.isEmpty()) {
            origin = originText.getHint().toString();
        }

        String destination = destinationText.getText().toString();
        if (destination.isEmpty()) {
            destination = destinationText.getHint().toString();
        }

        String priceString = priceText.getText().toString();
        if (priceString.isEmpty()) {
            priceString = priceText.getHint().toString();
        }
        String numSeatsString = numSeatsText.getText().toString();
        if (numSeatsString.isEmpty()) {
            numSeatsString = numSeatsText.getHint().toString();
        }

        Flight flight = null;
        Date departureDate = null;
        Date arrivalDate = null;
        try {
            departureDate = dateTime.parse(departureDateString);
        } catch (ParseException e1) {

            departureDateText.setError("Incorrect Format!");
        }
        try {
            arrivalDate = dateTime.parse(arrivalDateString);
        } catch (ParseException e2) {
            arrivalDateText.setError("Incorrect Format!");
        }


        if(!(departureDateText.getError() == null)|| !(arrivalDateText.getError() == null)){
            return;
        }

        double price = Double.parseDouble(priceString);
        Integer numSeats = Integer.parseInt(numSeatsString);

        flight = new Flight(flightNumber, departureDate, arrivalDate, airline,
                origin, destination, price, numSeats);
        Database.mapFlights.put(flightNumber, flight);

        Toast toast = Toast.makeText(getApplicationContext(), "Flight Updated!",
                Toast.LENGTH_LONG);
        toast.show();
        finish();
        }
}
