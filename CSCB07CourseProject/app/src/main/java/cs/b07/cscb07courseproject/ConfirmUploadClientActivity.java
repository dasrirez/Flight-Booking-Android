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

import managers.ClientManager;

public class ConfirmUploadClientActivity extends AppCompatActivity {

    private File clientsFile;
    private String filePath;
    private ClientManager clientManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload_client);
        Intent intent = getIntent();
        clientsFile = (File) intent.getSerializableExtra(AdminPanelActivity.CLIENT_KEY);
        filePath = clientsFile.toString();

        String content = "";
        try {
            content = clientManager.readFromFile(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TextView view = (TextView) findViewById(R.id.client_info);
        view.setText(content);
        view.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Uploads the data
     * @param view the view
     */
    public void uploadData(View view){
        try {
            clientManager = new ClientManager(clientsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
}