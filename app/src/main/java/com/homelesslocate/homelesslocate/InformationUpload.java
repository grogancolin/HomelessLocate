package com.homelesslocate.homelesslocate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class InformationUpload extends Activity {

    double[] locToSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_upload);

        locToSend = getIntent().getDoubleArrayExtra("location");

        Spinner spinner =  (Spinner)findViewById(R.id.numberSpinner);
        String[] items = new String[]{"1", "2", "3", "4", "5+"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    public void onClick(View view){
        try {
            Map<String, String> postParams = new HashMap<String, String>();
            postParams.put("lat", String.valueOf(locToSend[0]));
            postParams.put("lon", String.valueOf(locToSend[1]));
            postParams.put("numberOfPeople", ((Spinner) findViewById(R.id.numberSpinner)).getSelectedItem().toString());
            postParams.put("additionalInformation", ((EditText) findViewById(R.id.additionalInfoInput)).getText().toString());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));

            postParams.put("clientTime", df.format(new Date()));

            final InformationUpload myClass = this;
            sendInfo("http://10.0.2.2:8080/newLocation", postParams, view, new VolleyCallback() {
                @Override
                public void onSuccess(boolean result) {
                    Intent i = new Intent(myClass, HomeActivity.class);
                    i.putExtra("success", result);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });
        } catch (Exception e){}

    }
    public interface VolleyCallback{
        void onSuccess(boolean result);
    }

    public void sendInfo(String url, Map<String, String> postData, View view, final VolleyCallback cb){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest =
            new JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(postData),
                new Response.Listener<JSONObject>(){
                    @Override public void onResponse(JSONObject response) {
                        try {
                            final boolean result = response.getBoolean("success");

                            cb.onSuccess(result);
                            Log.i("InformationUpload", response.toString());
                        } catch(Exception e){}
                    }
                },
                new Response.ErrorListener(){
                    @Override public void onErrorResponse(VolleyError e){
                        Log.e("InformationUpload", e.getMessage());
                    }
                }) {
            @Override public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type", "application/json");
                headers.put("charset", "utf-8");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
