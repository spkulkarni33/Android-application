/*******************************************************************************
 * FileIO.java: Java class to handle Input/Output. File used is "GameScores.txt"
 *
 * This class implements two methods, readFile and writeFile.
 *
 * readFile() accepts the current context of the application. Reads the file
 * and returns list of Player objects (ArrayList<Player>).
 *
 * writeFile() accepts list of player objects and writes the data to the file.
 * The file written is tab separated. Columns are Name, Score, Date.
 *
 * Written by Sanket Kulkarni (spk170001) at The University of Texas at Dallas
 * starting March 10, 2020.
 ******************************************************************************/

package com.example.spk170001_asg5;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileIO extends AppCompatActivity{
    private static ArrayList<Player> players = new ArrayList<>();;
    private static Player player;

    // readFile() accepts the current context of the application. Reads the file
    // and returns list of Player objects (ArrayList<Player>).
    public static ArrayList<Player> readFile(Context ApplicationContext)
    {
        try{
            players.clear();
            String line=null;
            FileInputStream file = ApplicationContext.openFileInput("GameScores.txt");
            InputStreamReader input = new InputStreamReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);
            while(true)
            {
                line = bufferedReader.readLine();
                if(line == null)
                {
                    break;
                }
                else
                {
                    String[] temp = line.split("\t");
                    player = new Player(temp[0], temp[1], temp[2]);
                    players.add(player);
                }
            }
            bufferedReader.close();
            input.close();
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return players;
    }

    // writeFile() accepts the application context and list of player objects.
    //  Writes the data to the file as tab separated.
    public static void writeFile(Context ApplicationContext, ArrayList<Player> data)
    {
        try{
            FileOutputStream file = ApplicationContext.openFileOutput("GameScores.txt", MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);
            for(int i = 0; i<data.size();i++)
            {
                String name = data.get(i).getName();
                String score = data.get(i).getScore();
                String date = data.get(i).getDate();
                outputStreamWriter.write(name + "\t" + score + "\t" + date + "\n");
            }
            outputStreamWriter.close();
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
