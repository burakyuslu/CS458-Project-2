package com.example.covid_survey;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.*;

public class MainActivity extends AppCompatActivity {

    final static String DATE_FORMAT = "dd-MM-yyyy";
    final static List<String> cities = Arrays.asList("ankara", "izmir", "istanbul", "bursa", "eskişehir",
            "antalya", "gaziantep", "mersin", "hatay", "trabzon", "samsun", "sinop", "rize", "zonguldak");

    private EditText nameET;
    private EditText birthdayET;
    private EditText cityET;
    private String selectedVaccine = "-";
    private EditText sideEffectET;
    private TextView sideEffectTW;
    private EditText historyET;
    private TextView historyTW;
    private TextView errorText;

    private List<Object> database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        nameET = (EditText) findViewById(R.id.editTextName);
        birthdayET = (EditText) findViewById(R.id.editTextDate);
        cityET = (EditText) findViewById(R.id.city);
        sideEffectET = (EditText) findViewById(R.id.sideEffects);
        sideEffectTW = (TextView) findViewById(R.id.textView8);
        historyET = (EditText) findViewById(R.id.history);
        historyTW = (TextView) findViewById(R.id.textViewHistory);
        errorText = (TextView) findViewById(R.id.errorText);

        // make history & side effect question invisible until yes is selected in question
        historyET.setVisibility(View.INVISIBLE);
        historyTW.setVisibility(View.INVISIBLE);

        sideEffectET.setVisibility(View.INVISIBLE);
        sideEffectTW.setVisibility(View.INVISIBLE);

        Button sendButton = (Button) findViewById(R.id.send);

        database = new LinkedList<Object>();

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
                System.out.println("Participant added. ");
                // Do something in response to button click
                Map<String, String> participant = getAllFields();
                if(participant == null){
                    //error message
                    System.out.println("EMPTY FIELDS");
                    errorText.setText("All fields need to be filled.");
                } else {
                    //send to db
                    System.out.println(participant.toString());
                    if (database != null && database.contains(participant)) {
                        System.out.println("DUPLICATE DATA");
                        errorText.setText("You have send your response already!");
                    } else {
                        if (!isStringValid(Objects.requireNonNull(participant.get("name")))) {
                            System.out.println("INVALID INPUT");
                            errorText.setText("Name field is invalid.");
                        } else if( ! isDateValid(birthdayET.getText().toString()) ) {
                            System.out.println("INVALID INPUT");
                            errorText.setText("Birthday date is invalid.");
                        } else if( !isCityValid(cityET.getText().toString()) ) {
                            System.out.println("INVALID INPUT");
                            errorText.setText("City name is invalid or unavailable.");
                        } else {
                            database.add(participant);
                            System.out.println("Participant added. ");
                            errorText.setText("Your response has been send!");
                        }
                    }
                    System.out.println("DONE");
                }
            }
        });

    }



    public Map<String, String> getAllFields() {

        String nameValue = nameET.getText().toString();
        String birthdayValue = birthdayET.getText().toString();
        String cityValue = cityET.getText().toString();
        String sideEffectValue = sideEffectET.getText().toString();
        String historyValue = historyET.getText().toString();
        String genderValue = getRadioGenderChecked();
        String vaccValue = getRadioVaccChecked();
        String thirdVaccValue = getRadioThirdVaccChecked();

        boolean missingField = nameValue.isEmpty() || birthdayValue.isEmpty() || cityValue.isEmpty()
                || vaccValue.equals("-") || (vaccValue.equals("yes") && sideEffectValue.isEmpty())
                || genderValue.equals("-")  || thirdVaccValue.equals("-") || selectedVaccine.equals("-")
                || (thirdVaccValue.equals("yes") && historyValue.isEmpty()) ;

        if(missingField)
            return null;

        Map<String, String> dict = new HashMap<String, String>();
        dict.put("name", nameValue);
        dict.put("birthday", birthdayValue);
        dict.put("city", cityValue);
        dict.put("gender", genderValue);
        dict.put("hadVaccine", vaccValue);
        dict.put("vaccine", selectedVaccine);
        dict.put("sideEffects", vaccValue.equals("no")  ? " " : sideEffectValue);
        dict.put("hadThird", thirdVaccValue);
        dict.put("history",  thirdVaccValue.equals("no") ? " " : historyValue );
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

    public void onThirdClicked(View view) {
        // Is the button now checked?
        boolean yesChecked = ((RadioButton) findViewById(R.id.thirdYes)).isChecked();
        boolean noChecked = ((RadioButton) findViewById(R.id.thirdNo)).isChecked();
        if(yesChecked) {
            historyET.setVisibility(View.VISIBLE);
            historyTW.setVisibility(View.VISIBLE);
        }
        if (noChecked) {
            historyET.setVisibility(View.INVISIBLE);
            historyTW.setVisibility(View.INVISIBLE);
        }
    }

    public void onVaccClicked(View view) {
        // Is the button now checked?
        boolean yesChecked = ((RadioButton) findViewById(R.id.vaccYes)).isChecked();
        boolean noChecked = ((RadioButton) findViewById(R.id.vaccNo)).isChecked();
        if(yesChecked) {
            sideEffectET.setVisibility(View.VISIBLE);
            sideEffectTW.setVisibility(View.VISIBLE);
        }
        if (noChecked) {
            sideEffectET.setVisibility(View.INVISIBLE);
            sideEffectTW.setVisibility(View.INVISIBLE);
        }
    }

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            Date bday = df.parse(date);
            Date today = new Date();
            return bday.before(today);
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isStringValid(String s)
    {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!(Character.isLetter(s.charAt(i))) && !(s.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCityValid(String s)
    {
        if( isStringValid(s) ) {
            return cities.contains(s.toLowerCase(Locale.ROOT));
        }
        return false;
    }
}

