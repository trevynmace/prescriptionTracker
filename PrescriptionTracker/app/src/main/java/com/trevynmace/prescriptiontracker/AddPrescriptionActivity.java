package com.trevynmace.prescriptiontracker;

import android.app.DatePickerDialog;
import android.app.IntentService;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class AddPrescriptionActivity extends AppCompatActivity
{
    Calendar cal;

    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener time;

    EditText calendarEditText;
    EditText timeEditText;

    String name;
    int selectedRadioButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prescription_activity);

        //get prescription name
        EditText nameView = (EditText) findViewById(R.id.prescription_name);
        name = String.valueOf(nameView.getText());

        //get selected frequency
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.frequency_radio_group);
        selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        addCalendarAndTimePickerStuff();

        Button saveButton = (Button) findViewById(R.id.save_prescription_button);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String prescriptionData = name + "," + selectedRadioButtonId + "," + calendarEditText.getText() + "," + timeEditText.getText();

                SharedPreferences.Editor editor = getSharedPreferences(AppData.preferencesString, MODE_PRIVATE).edit();
                editor.putString(name, prescriptionData);
                AppData.prescriptionNames.add(name);

                //TODO: can't find this view from this activity....
                Snackbar.make(findViewById(R.id.main_activity_layout), name + " has been saved!", Snackbar.LENGTH_SHORT).show();

                //return back to previous activity;
                finish();
            }
        });
    }

    private void addCalendarAndTimePickerStuff()
    {
        calendarEditText = (EditText) findViewById(R.id.calendar_edit_text);
        timeEditText = (EditText) findViewById(R.id.time_edit_text);

        cal = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        calendarEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(AddPrescriptionActivity.this, "on calendarEditText click BEFORE date picker", Toast.LENGTH_SHORT).show();
                new DatePickerDialog(AddPrescriptionActivity.this, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
            {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };

        timeEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO: need to figure out 24 hour clock vs 12 hour
                new TimePickerDialog(AddPrescriptionActivity.this, time, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void updateDateLabel()
    {
        String format = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);

        calendarEditText.setText(simpleDateFormat.format(cal.getTime()));
    }

    private void updateTimeLabel()
    {
        String formattedTime = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
        timeEditText.setText(formattedTime);
    }
}