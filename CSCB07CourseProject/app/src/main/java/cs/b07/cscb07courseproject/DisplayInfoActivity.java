package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import user.Client;

public class DisplayInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        Intent intent = getIntent();
        Client client = (Client) intent.getSerializableExtra("clientKey");
        String clientString = client.toString();

        TextView newlyRegistered = (TextView) findViewById(R.id.newly_registered);
        newlyRegistered.setText(clientString);
    }


}