package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.Database;
import flight.Flight;
import flight.Itinerary;

public class ListActivity extends AppCompatActivity {

    private Database database;
    private ListView listView;
    private String email;
    private String itineraryDate;
    private String origin;
    private String destination;
    private String sortType;
    private String sortOrder;
    private String searchMode;
    private List<Itinerary> selectedItineraries;
    private ArrayAdapter<Itinerary> itineraryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = new Database();

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra(MainActivity.EMAIL_KEY);
        itineraryDate = (String) intent.getSerializableExtra(FlightBookingActivity.DATE_KEY);
        origin = (String) intent.getSerializableExtra(FlightBookingActivity.ORIGIN_KEY);
        destination = (String) intent.getSerializableExtra(FlightBookingActivity.DESTINATION_KEY);
        sortType = (String) intent.getSerializableExtra(FlightBookingActivity.SORT_TYPE_KEY);
        sortOrder = (String) intent.getSerializableExtra(FlightBookingActivity.SORT_ORDER_KEY);
        searchMode = (String) intent.getSerializableExtra(FlightBookingActivity.SEARCH_MODE_KEY);


        listView = (ListView) findViewById(R.id.list_search_results);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Itinerary itinerary = (Itinerary) adapterView.getItemAtPosition(i);
                database.bookItinerary(email, itinerary);
                Toast.makeText(getApplicationContext(), "Itinerary booked!", Toast.LENGTH_SHORT).show();
            }
        });

        if (searchMode.equals(FlightBookingActivity.ITINERARY_MODE)) {
            makeItineraryList();
        } else {
            makeFlightList();
        }
    }

    /**
     * Populates the listview containing direct flights
     */
    private void makeFlightList() {
        List<Flight> selectedFlights = database.getFlights(itineraryDate, origin, destination);
        selectedItineraries = new ArrayList<>();

        for (Flight flight : selectedFlights) {
            selectedItineraries.add(new Itinerary(flight));
        }

        // manual sorting
        if (sortType.equals(FlightBookingActivity.COST_MODE))
            Collections.sort(selectedItineraries, Itinerary.compareCost());
        else
            Collections.sort(selectedItineraries, Itinerary.compareTime());

        if (sortOrder.equals(FlightBookingActivity.DESCENDING_MODE))
            Collections.reverse(selectedItineraries);

        itineraryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                selectedItineraries);

        listView.setAdapter(itineraryAdapter);
    }

    /**
     * Populates the listview containing itineraries with indirect flights.
     */
    private void makeItineraryList() {
        if (sortType.equals(FlightBookingActivity.COST_MODE))
            selectedItineraries = database.getItinerariesByCost(itineraryDate,
                    origin, destination);
        else
            selectedItineraries = database.getItinerariesByTime(itineraryDate,
                    origin, destination);

        if (sortOrder.equals(FlightBookingActivity.DESCENDING_MODE))
            Collections.reverse(selectedItineraries);

        itineraryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                selectedItineraries);

        listView.setAdapter(itineraryAdapter);
    }
}
