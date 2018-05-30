package com.example.srivi.hw1_groups28;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.srivi.hw1_groups28.R.drawable.ic_launcher_happy;

public class MainActivity extends AppCompatActivity {

    EditText billEdit;
    String billString;
    RadioGroup tip;
    int tipId;
    SeekBar seekBar;
    TextView tipSet;
    TextView totalSet;
    TextView seekText;
    Button exit;
    double tipValue;
    double tipTotal;
    double billTotal;
    double billValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Tip Calculator");
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher_happy);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            billEdit = (EditText) findViewById(R.id.editText2);
            billString = billEdit.getText().toString();
            billEdit.addTextChangedListener(textWatcher);
            tip = (RadioGroup) findViewById(R.id.radioGroup);
            tipId = tip.getCheckedRadioButtonId();
            seekBar = (SeekBar) findViewById(R.id.seekBar);
            tipSet = (TextView) findViewById(R.id.textView8);
            totalSet = (TextView) findViewById(R.id.textView9);
            exit = (Button) findViewById(R.id.button);

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            tip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    tipId = i;
                    if (billNotEmpty() && validString())
                        calculate();
                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    seekText = (TextView) findViewById(R.id.textView4);
                    seekText.setText(seekBar.getProgress() + "%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (billNotEmpty() && validString())
                        calculate();
                }
            });
        }catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Exception occurred", Toast.LENGTH_SHORT ).show();
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            billString = billEdit.getText().toString();
            Log.d("Bill", billString);
            if(billNotEmpty() && validString())
                calculate();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    public void calculate() {
        if(tipId==R.id.radioButton5) tipValue = 0.10;
        else if(tipId == R.id.radioButton6) tipValue = 0.15;
        else if(tipId == R.id.radioButton7) tipValue = 0.18;
        else {
            tipValue = seekBar.getProgress()/100.0;
        }

        billValue = Double.parseDouble(billString);
        tipTotal = tipValue*billValue;
        billTotal = billValue+tipTotal;
        tipSet.setText(String.valueOf(tipTotal));
        totalSet.setText(String.valueOf(billTotal));
    }

    public boolean billNotEmpty() {
        if(billString.equals("")) {
            billEdit.setError("Enter Bill Total");
            return false;
        }
        return true;
    }

    public boolean validString() {
        char c = 'a';
        for(int i=0;i<billString.length();i++) {
            c = billString.charAt(i);
            if (c >= '0' && c <= '9') {

            } else {
                Toast.makeText(getApplicationContext(), "Please enter a valid bill", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}