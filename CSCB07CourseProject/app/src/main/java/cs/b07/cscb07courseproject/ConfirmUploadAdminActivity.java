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

import managers.AdminManager;

public class ConfirmUploadAdminActivity extends AppCompatActivity {

    private File adminsFile;
    private String filePath;
    private AdminManager adminManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload_admin);
        Intent intent = getIntent();
        adminsFile = (File) intent.getSerializableExtra(MasterInputActivity.ADMIN_KEY);
        filePath = adminsFile.toString();

        String content = "";
        try {
            content = adminManager.readFromFile(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TextView view = (TextView) findViewById(R.id.admin_info);
        view.setText(content);
        view.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Uploads the data
     * @param view the view
     */
    public void uploadData(View view){
        try {
            adminManager = new AdminManager(adminsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
}
