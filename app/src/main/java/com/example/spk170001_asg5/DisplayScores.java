/******************************************************************************
 * Android Application to display top 12 scores.
 *
 * This program displays the top 12 high scores. It will display the name,
 * score, and the date that the score was achieved. You can add a high score
 * by pressing the ADD button on the toolbar.
 *
 * In case of conflicting high scores, the most recent dates takes preference.
 * Only the top 12 scores are displayed on the main screen of the application.
 *
 * Scores are written to and read from a tab-separated file called "HighScores.txt".
 * The file is updated (written) every time a new score is added. Only the top 12
 * scores are written to the file. The scores are read from the file once the app
 * is launched.
 *
 * Written by Sanket Kulkarni (spk170001) at The University of Texas at Dallas
 * starting March 10, 2020.
 ******************************************************************************/

package com.example.spk170001_asg5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class DisplayScores extends AppCompatActivity {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> scores = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayAdapter<String> n;
    ArrayAdapter<String> s;
    ArrayAdapter<String> d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_scores);
        Button add = findViewById(R.id.addbtn);
        players = new ArrayList<Player>();
        players = FileIO.readFile(getApplicationContext());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        Intent data = getIntent();
        String from = data.getStringExtra("id");
        if(from.equals("MainActivity"))
        {
            //players.clear();
            displayscores();
        }
        else if(from.equals("EnterRecord")) {
            String name = data.getStringExtra("name");
            String score = data.getStringExtra("score");
            String date = data.getStringExtra("date");
            Player player = new Player(name, score, date);
            //players = FileIO.readFile(getApplicationContext());
            players.add(player);
            Collections.sort(players, Player.compareDates);
            Collections.sort(players, Player.compareScores);
            displayscores();
            FileIO.writeFile(getApplicationContext(), players);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "New Score has been added",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    //This function sets the ArrayAdapters to the respective listviews. The data to be used is
    //in the form of ArrayList<String>.
    public void displayscores()
    {
        names.clear();
        scores.clear();
        dates.clear();
        int size;
        if(players.size() < 12)
        {
            size = players.size();
        }
        else
        {
            size = 12;
        }
        for(int i = 0; i<size; i++)
        {
            names.add(players.get(i).getName());
            scores.add(players.get(i).getScore());
            dates.add(players.get(i).getDate());
        }
        n = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        ListView lview = findViewById(R.id.listNames);
        lview.setAdapter(n);

        s = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores);
        ListView ls = findViewById(R.id.listScores);
        ls.setAdapter(s);

        d = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dates);
        ListView ld = findViewById(R.id.listDates);
        ld.setAdapter(d);
    }

}
