/******************************************************************************
 * An Android Game: Pop the balloons!
 *
 * This program is a relatively simple game. The game play
 * will display shapes of various colors, and the objective
 * is to touch only the correct ones to make them disappear.
 * The user has to touch 10 RED squares as fast as
 * possible! Lower times mean a better score.
 *
 * Scores can be added to the high scores chart.
 *
 * In case of conflicting high scores, the most recent dates takes preference.
 * Only the top 12 scores are displayed on the score screen of the application.
 *
 * Instructions appear on the starting page of the application.
 *
 * Written by Sanket Kulkarni (spk170001) at The University of Texas at Dallas
 * starting March 20, 2020.
 ******************************************************************************/

package com.example.spk170001_asg5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView instructions = findViewById(R.id.instructions);
        instructions.setText("1. Press on the RED shaped squares only.\n\n" +
                "2. If you miss a red square, lifetime \n of the square will be added to your score.\n\n" +
                "3. Try to pop 10 red square as fast as you can!\n\n" +
                "4. Lower the time consumed, higher the score!");
        Button play = findViewById(R.id.play_btn);
        Button viewscore = findViewById(R.id.viewscores);

        // user opts to play the game. Send intent to start GameCanvas activity and load the canvas
        // to the screen.
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameCanvas.class);
                startActivity(intent);
            }
        });

        // Display the high scores list to the user.
        viewscore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisplayScores.class);
                intent.putExtra("id", "MainActivity");
                startActivity(intent);
            }
        });

    }
}
