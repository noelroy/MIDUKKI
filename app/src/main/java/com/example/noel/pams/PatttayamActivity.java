package com.example.noel.pams;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PatttayamActivity extends AppCompatActivity {

    EditText appNo;
    int appIntNo;
    int resDigit, checkDigit,calDigit,orgDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patttayam);
    }

    public void checkNumber(View view) {
        try {
            appNo = (EditText) findViewById(R.id.editText);
            appIntNo = Integer.parseInt(appNo.getText().toString().trim());
        }
        catch (Exception e) {
            invalidRegNo();
            return;
        }
        orgDigit = appIntNo;

        /*pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);*/

        resDigit = appIntNo / 1000000;
        checkDigit = appIntNo % 1000;
        calDigit = (checkDigit % 10) * 3 ;
        checkDigit = checkDigit /10;
        calDigit = calDigit + (checkDigit % 10);
        checkDigit = checkDigit /10;
        calDigit = calDigit + 8 + checkDigit + 9;

        if(resDigit != calDigit) {
            invalidRegNo();
        }
        else {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("APPID",orgDigit);
            startActivity(intent);
        }
    }

    void invalidRegNo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enter a valid Register Number.")
                .setTitle("Invalid Register Number");
        builder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
