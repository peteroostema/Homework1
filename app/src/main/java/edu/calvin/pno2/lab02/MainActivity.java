package edu.calvin.pno2.lab02;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.R.array;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

import static edu.calvin.pno2.lab02.R.*;
/* Homework1 implements a two value calculator with operators +, -, *, and /
 * Author: Peter Oostema
 * Date: September 29, 2016
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private EditText valueEditText;
    private EditText valueEditText2;
    private TextView resultTextView;
    private Button calculateButton;
    private Spinner spinner;

    private String valueAmountString = "";
    private String valueAmountString2 = "";
    private String valueOperator = "";

    private SharedPreferences savedValues;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        // variables initialized to a layout item
        valueEditText = (EditText) findViewById(id.editText);
        valueEditText2 = (EditText) findViewById(id.editText2);
        resultTextView = (TextView) findViewById(id.textView2);
        calculateButton = (Button) findViewById(id.button);
        spinner = (Spinner) findViewById(id.spinner);

        // setup spinner with choices
        String[] spinnerItems = {"+", "-", "*", "/"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        calculateButton.setOnClickListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("valueAmountString", valueAmountString);
        editor.putString("valueAmountString2", valueAmountString2);
        editor.putString("valueOperator", valueOperator);
        editor.apply();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        valueAmountString = savedValues.getString("valueAmountString", "");
        valueAmountString2 = savedValues.getString("valueAmountString", "");
        valueOperator = savedValues.getString("valueOperator", "");

        valueEditText.setText(valueAmountString);
        valueEditText2.setText(valueAmountString2);

        calculateAndDisplay();
    }

    public void calculateAndDisplay() {
        valueAmountString = valueEditText.getText().toString();
        valueAmountString2 = valueEditText2.getText().toString();
        valueOperator = (String) spinner.getSelectedItem();
        float value;
        float value2;
        if (valueAmountString.equals("") && valueAmountString2.equals("")) {
            value = 0;
            value2 = 0;
        } else {
            value = Float.parseFloat(valueAmountString);
            value2 = Float.parseFloat(valueAmountString2);
        }

        NumberFormat number = NumberFormat.getNumberInstance();
        if (valueOperator == "+") { // check operator for appropriate calculation
            resultTextView.setText(number.format(value + value2));
        } else if (valueOperator == "*") {
            resultTextView.setText(number.format(value * value2));
        } else if (valueOperator == "-") {
            resultTextView.setText(number.format(value - value2));
        } else {
            resultTextView.setText(number.format(value / value2));
        }
    }

    //@Override
    public void onClick(View v) {
        calculateAndDisplay();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
