package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Result variable to store the correct_responses.
    int result = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = (Button) findViewById(R.id.submit);
        submit = (Button) findViewById(R.id.submit);


    }

    /*
        Question 1
        If The All Valid Checkboxes Are Selected And The Wrong Is NOT Checked, Increment 1 To Result.
        */
    private void computeQ1() {
        //Question 1
        CheckBox q1_a = (CheckBox) findViewById(R.id.Q1zeus);

        CheckBox q1_c = (CheckBox) findViewById(R.id.Q1Hades);
        CheckBox q1_d = (CheckBox) findViewById(R.id.Q1Hercules);

        if (q1_a.isChecked() && q1_c.isChecked() && q1_d.isChecked() && !q1_d.isChecked()) {
            result += 1;
        } else {
            result += 0;
        }


    }

    /*
    Question 2
    Correct Response Increments 1 To Result.
    */
    private void computeQ2() {
        RadioButton q2_a = (RadioButton) findViewById(R.id.Q2Frozen);
        RadioButton q2_b = (RadioButton) findViewById(R.id.Q2DM3);
        RadioButton q2_c = (RadioButton) findViewById(R.id.Q2Minions);
        RadioButton q2_d = (RadioButton) findViewById(R.id.Q2WRalph);

        if (q2_a.isChecked() && !q2_b.isChecked() && !q2_c.isChecked() && !q2_d.isChecked()) {
            result += 1;
        } else {
            result += 0;
        }

    }

    /*
    Question 3
    If The Value In The EditText Is Correct,Increment 1 To Result.
    */
    private void computeQ3() {
        EditText q3_a = (EditText) findViewById(R.id.Q3Ans);
        String ans = q3_a.getText().toString();
        if (ans.trim().equals("0")) {
            result += 1;
        } else {
            result += 0;
        }
    }


    public void showResult(View view) {
        //Compute The Result
        computeQ1();
        computeQ2();
        computeQ3();
    //Display  Result Message
        Toast.makeText(this, "Result : " + result, Toast.LENGTH_LONG).show();
    }


    /*
    Question 4
    If Correct Button Response Clicked,add 1 to result.
    */
    public void computeQ4(View view) {
        Button q4_d = (Button) findViewById(R.id.Q4_d);
        q4_d.setBackgroundColor(getResources().getColor(R.color.gray));
        result += 1;
    }


}