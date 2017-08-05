package com.example.noel.pams;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    TextView mTextView1,mTextView2,mTextView3,mTextView4;

    int APPID;

    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        mTextView1 = (TextView) findViewById(R.id.mTextView1);
        mTextView2 = (TextView) findViewById(R.id.mTextView2);
        mTextView3 = (TextView) findViewById(R.id.mTextView3);
        mTextView4 = (TextView) findViewById(R.id.mTextView4);

        APPID = getIntent().getIntExtra("APPID", 0);
        mTextView1.setText(String.valueOf(APPID));

        volleyMethod();
    }

    private void volleyMethod() {
        showpDialog();
        String url = "http://palms.gov.in/searchapp.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String error = jsonObject.getString("error");
                    if(error.equalsIgnoreCase("null")) {
                        mTextView1.setText("വിവരങ്ങൾ ലഭ്യമല്ല");
                    }
                    else {
                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String status = jsonObject.getString("status");
                        mTextView2.setText(name);
                        mTextView3.setText(address);
                        status = status.replace("~","\n");
                        mTextView4.setText(status);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + "Something went Wrong. Please try again",
                            Toast.LENGTH_LONG).show();
                    Log.d("EXCEPTION",e.toString());
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(),
                        "ERROR: Check your network", Toast.LENGTH_LONG).show();
                Log.d("ERROR",error.toString());
                hidepDialog();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                Log.d("RegNo",String.valueOf(APPID));
                params.put("regnofull",String.valueOf(APPID));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 ( compatible ) ");
                params.put("Accept", "*/*");

                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
