package com.trevynmace.prescriptiontracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddPrescriptionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prescription_activity);

        //TODO: need to get save button and other elements so we can save that data to the preferences api and then load that data into the list on the main activity
    }
}