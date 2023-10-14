package com.example.bmicalculatorg3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {

    DecimalFormat formatter = new DecimalFormat("#,###.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edit_weight = (EditText)findViewById(R.id.editWeight);
        edit_weight.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        EditText edit_height = (EditText)findViewById(R.id.editHeight);
        edit_weight.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        final Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit_weight.getText().toString().isEmpty() && !edit_height.getText().toString().isEmpty()) {
                    final float weight = Float.parseFloat(edit_weight.getText().toString());
                    final float height = Float.parseFloat(edit_height.getText().toString()) / 100;
                    final float bmi = Float.parseFloat(formatter.format(weight / (height * height)));
                    //set bmi
                    final TextView bmiTextView = findViewById(R.id.BMI);
                    bmiTextView.setText(Float.toString(bmi));
                    //change color text view background
                    final TextView criteria = findViewById(R.id.Criteria);
                    if (bmi < 18.5) {
                        criteria.setText(getString(R.string.underweight));
                        criteria.setBackgroundColor(getColor(R.color.red));
                    } else if (bmi < 23) {
                        criteria.setText(getString(R.string.healthy));
                        criteria.setBackgroundColor(getColor(R.color.green));
                    } else if (bmi < 25) {
                        criteria.setText(getString(R.string.obesitylv1));
                        criteria.setBackgroundColor(getColor(R.color.yellow));
                    } else if (bmi < 30) {
                        criteria.setText(getString(R.string.obesitylv2));
                        criteria.setBackgroundColor(getColor(R.color.orange));
                    } else if (bmi > 30) {
                        criteria.setText(getString(R.string.obesitylv3));
                        criteria.setBackgroundColor(getColor(R.color.red));
                    }
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final TextView txtView=findViewById(R.id.textView);
        txtView.setTextSize(newConfig.fontScale*32);
    }

}

class DecimalDigitsInputFilter implements InputFilter {
    private Pattern mPattern;
    DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) +
                "})?)||(\\.)?");
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }
}
