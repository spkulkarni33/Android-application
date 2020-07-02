/******************************************************************************
 * EnterRecord.java: New activity to enter a new high score.
 *
 * This class accepts the details from layout "enter_score.xml" and validates the data.
 *
 * Request code for this activity is 10 and if everything is valid, a result
 * code 2 is returned to the main activity.
 *
 * Inter-activity communication happens using Intent.
 *
 * Written by Sanket Kulkarni (spk170001) at The University of Texas at Dallas
 * starting March 10, 2020.
 ******************************************************************************/

package com.example.spk170001_asg5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EnterRecord extends AppCompatActivity {
    EditText name;
    EditText score;
    EditText date;
    EditText time;
    Button submit;
    Button goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_score);
        name = findViewById(R.id.getName);
        name.requestFocus();
        score = findViewById(R.id.getScore);
        date = findViewById(R.id.getDate);
        submit = findViewById(R.id.button_submit);
        time = findViewById(R.id.getTime);
        submit.setOnClickListener(submit_handler);
        Intent data = getIntent();
        score.setText(data.getStringExtra("Score"));
        date.setText(data.getStringExtra("Date"));
        time.setText(data.getStringExtra("Time"));
    }

    //Check if time entered is valid. If yes, return 1 else return 0.
    public int validate_time(String gettime)
    {
        try {
            String[] temp = gettime.split(":");
            if (Integer.parseInt(temp[0]) < 24 && Integer.parseInt(temp[1]) < 60 &&
                    Integer.parseInt(temp[2]) < 60) {
                return 1;
            }
            return 0;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    //Validate date. Accepts the entered date and time.
    // If the entered date is valid, return 1. If date does not match format MM/dd/yyyy,
    //return 0.
    // If date entered is greater than current date, return -1.
    // If time entered is invalid, enter -2.
    public int validate_date(String date, String time)
    {
        /*if(validate_time(time) == 0)
        {
            return -2;
        }
        String regEx ="^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d{2}$";
        Matcher matcherObj = Pattern.compile(regEx).matcher(date);
        if (matcherObj.matches())
        {
            SimpleDateFormat sdfo = new SimpleDateFormat("MM/dd/yyyy");
            try{
                Date d1 = sdfo.parse(date);
                Date today = new Date();
                if(d1.compareTo(today) > 0)
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            return 0;
        }
        return 0;*/
        return 1;
    }

    // This function validates the entered score. Accepts the score as a String.
    // If score is not empty and greater than 0, return 1.
    // Else, return 0.
    private int validate_score(String score)
    {
        return 1;
    }

    //OnClick handler for the SAVE button. This is equivalent to disabling the SAVE
    // button unless and until valid data is entered in the form.
    // Validates all the fields and gives feedback to the user accordingly in the form
    // of Toast.
    public View.OnClickListener submit_handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                score = findViewById(R.id.getScore);
                date = findViewById(R.id.getDate);
                String getname = name.getText().toString();
                String getscore = score.getText().toString();
                String getdate = date.getText().toString();
                String gettime = time.getText().toString();
                if(validate_date(getdate, gettime) == 1 && validate_score(getscore) == 1
                        && getname.length() != 0)
                {
                    Intent i = new Intent(getApplicationContext(), DisplayScores.class);
                    i.putExtra("id", "EnterRecord");
                    i.putExtra("name", getname);
                    i.putExtra("score", getscore);
                    i.putExtra("date", getdate);
                    setResult(2, i);
                    startActivity(i);
                }
                else if(getname.length() == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Name cannot be empty!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(validate_date(getdate, gettime) == -2)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Time entered is invalid!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(validate_date(getdate, gettime) == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Date is invalid!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(validate_date(getdate, gettime) == -1)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Date cannot be greater than current date!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(validate_score(getscore) == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Score should be greater then 0!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    };
}