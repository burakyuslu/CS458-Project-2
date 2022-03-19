package com.example.covid_survey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText nameET;
    private EditText birthdayET;
    private EditText cityET;
    private String selectedVaccine = "-";
    private EditText sideEffectET;
    private EditText historyET;

    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        nameET = (EditText) findViewById(R.id.editTextName);
        birthdayET = (EditText) findViewById(R.id.editTextDate);
        cityET = (EditText) findViewById(R.id.city);
        sideEffectET = (EditText) findViewById(R.id.sideEffects);
        historyET = (EditText) findViewById(R.id.history);

        sendButton = (Button) findViewById(R.id.send);

        // fill in spinner info
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vaccines_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVaccine = (String) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Hashtable<String, String> dict = getAllFields();
                if(dict == null){
                    //error message
                    System.out.println("EMPTY FIELDS");
                } else {
                    //send to db
                    System.out.println("\nIs my dictionary empty? : " + dict.isEmpty() + "\n");
                    System.out.println("\nSize of my_dict : " + dict.size());
                    System.out.println(dict.toString());
                }
            }
        });
    }

    public Hashtable<String, String> getAllFields() {
        Hashtable<String, String> dict = new Hashtable<String, String>();

        String nameValue = nameET.getText().toString();
        String birthdayValue = birthdayET.getText().toString();
        String cityValue = cityET.getText().toString();
        String sideEffectValue = sideEffectET.getText().toString();
        String historyValue = historyET.getText().toString();
        String genderValue = getRadioGenderChecked();
        String vaccValue = getRadioVaccChecked();
        String thirdVaccValue = getRadioThirdVaccChecked();

        boolean missingField = nameValue.isEmpty() || birthdayValue.isEmpty() || cityValue.isEmpty()
                || sideEffectValue.isEmpty() || historyValue.isEmpty() || genderValue.equals("-")
                || vaccValue.equals("-") || thirdVaccValue.equals("-") || selectedVaccine.equals("-");
        if(missingField)
            return null;

        dict.put("name", nameValue);
        dict.put("birthday", birthdayValue);
        dict.put("city", cityValue);
        dict.put("gender", genderValue);
        dict.put("hadVaccine", vaccValue);
        dict.put("vaccine", selectedVaccine);
        dict.put("sideEffects", sideEffectValue);
        dict.put("hadThird", thirdVaccValue);
        dict.put("history", historyValue);
        return dict;
    }

    public String getRadioGenderChecked() {
        // Is the button now checked?
        boolean maleChecked = ((RadioButton) findViewById(R.id.male)).isChecked();
        boolean femaleChecked = ((RadioButton) findViewById(R.id.female)).isChecked();
        boolean nonbinaryChecked = ((RadioButton) findViewById(R.id.nonbinary)).isChecked();

        // Check which radio button was clicked
        if (maleChecked)
            return "male";
        else if (femaleChecked)
            return "female";
        else if (nonbinaryChecked)
            return "nonbinary";
        else
            return "-";
    }

    public String getRadioVaccChecked() {
        // Is the button now checked?
        boolean yesChecked = ((RadioButton) findViewById(R.id.vaccYes)).isChecked();
        boolean noChecked = ((RadioButton) findViewById(R.id.vaccNo)).isChecked();

        // Check which radio button was clicked
        if (yesChecked)
            return "yes";
        else if (noChecked)
            return "no";
        else
            return "-";
    }

    public String getRadioThirdVaccChecked() {
        // Is the button now checked?
        boolean yesChecked = ((RadioButton) findViewById(R.id.thirdYes)).isChecked();
        boolean noChecked = ((RadioButton) findViewById(R.id.thirdNo)).isChecked();

        // Check which radio button was clicked
        if (yesChecked)
            return "yes";
        else if (noChecked)
            return "no";
        else
            return "-";
    }

}