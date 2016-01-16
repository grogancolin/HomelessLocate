package com.homelesslocate.homelesslocate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        
        if(i.hasExtra("success")) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

            TextView toastText = (TextView) layout.findViewById(R.id.toast_text);
            if (i.getBooleanExtra("success", false) == true) {

                layout.setBackgroundColor(0xFFFFFFCC);
                toastText.setText("Message received. Thank you");

            } else {
                layout.setBackgroundColor(0xFFFF0000);
                toastText.setText("An error occured. Try again later...");
            }

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }

    public void startLocate(View view){
        Intent i = new Intent(this, LocateActivity.class);
        startActivity(i);
    }

    public void viewLocations(View view){
        Intent i = new Intent(this, LocateActivity.class);

        startActivity(i);
    }
}
