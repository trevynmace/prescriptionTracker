package com.trevynmace.prescriptiontracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;

public class AddPrescriptionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prescription_activity);

        EditText nameView = (EditText) findViewById(R.id.prescription_name);
        final String name = String.valueOf(nameView.getText());

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.frequency_radio_group);
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        final String prescriptionData = String.valueOf(selectedRadioButtonId) + "," + String.valueOf(hour) + ":" + String.valueOf(minute);

        Button saveButton = (Button) findViewById(R.id.save_prescription_button);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = getSharedPreferences(AppData.preferencesString, MODE_PRIVATE).edit();
                editor.putString(name, prescriptionData);
                AppData.prescriptionNames.add(name);
            }
        });
    }
}