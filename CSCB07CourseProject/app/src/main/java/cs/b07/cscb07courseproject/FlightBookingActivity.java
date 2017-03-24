package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static cs.b07.cscb07courseproject.MainActivity.EMAIL_KEY;

public class FlightBookingActivity extends AppCompatActivity {

    public static final String DATE_KEY = "date";
    public static final String ORIGIN_KEY = "origin";
    public static final String DESTINATION_KEY = "destination";
    public static final String SORT_TYPE_KEY = "sort_cost";
    public static final String SORT_ORDER_KEY = "sort_order";
    public static final String SEARCH_MODE_KEY = "search_mode";

    public static final String ITINERARY_MODE = "itinerary";
    public static final String FLIGHT_MODE = "flight";
    public static final String COST_MODE = "cost";
    public static final String TRAVEL_TIME_MODE = "time";
    public static final String ASCENDING_MODE = "ascending";
    public static final String DESCENDING_MODE = "descending";


    /* fields */
    private Intent listActivityIntent;
    private String email;
    private DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private String itineraryDate;
    private String origin;
    private String destination;
    private String sortType;
    private String sortOrder;

    /* screen elements */
    private RadioButton radioSortAscending;
    private RadioButton radioSortCost;
    private EditText itineraryDateText;
    private EditText originText;
    private EditText destinationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_booking);

        listActivityIntent = new Intent(this, ListActivity.class);

        // fetch screen elements
        radioSortAscending = (RadioButton) findViewById(R.id.radio_ascending);
        radioSortCost = (RadioButton) findViewById(R.id.radio_cost);
        itineraryDateText = (EditText) findViewById(R.id.txt_itinerary_date);
        originText = (EditText) findViewById(R.id.txt_airport_origin);
        destinationText = (EditText) findViewById(R.id.txt_airport_destination);

        // default layout
        radioSortAscending.setChecked(true);
        radioSortCost.setChecked(true);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra(EMAIL_KEY);
    }

    /**
     * Gets the itineraries.
     * @param view the view
     */
    public void getItineraries(View view) {
        if (!getInfo())
            return;
        listActivityIntent.putExtra(SEARCH_MODE_KEY, ITINERARY_MODE);
        startActivity(listActivityIntent);
    }

    /**
     * Gets the direct itineraries.
     * @param view the view
     */
    public void getFlights(View view) {
        if (!getInfo())
            return;
        listActivityIntent.putExtra(SEARCH_MODE_KEY, FLIGHT_MODE);
        startActivity(listActivityIntent);
    }

    /**
     * Gets itinerary info.
     */
    private boolean getInfo() {
        origin = originText.getText().toString();
        itineraryDate = itineraryDateText.getText().toString();
        destination = destinationText.getText().toString();

        if (origin.isEmpty() || itineraryDate.isEmpty() || destination.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            date.parse(itineraryDate);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return false;
        }

        sortType = (radioSortCost.isChecked() ? COST_MODE : TRAVEL_TIME_MODE);
        sortOrder = (radioSortAscending.isChecked() ? ASCENDING_MODE : DESCENDING_MODE);

        listActivityIntent.putExtra(SORT_TYPE_KEY, sortType);
        listActivityIntent.putExtra(SORT_ORDER_KEY, sortOrder);
        listActivityIntent.putExtra(EMAIL_KEY, email);
        listActivityIntent.putExtra(DATE_KEY, itineraryDate);
        listActivityIntent.putExtra(ORIGIN_KEY, origin);
        listActivityIntent.putExtra(DESTINATION_KEY, destination);
        return true;
    }
}
