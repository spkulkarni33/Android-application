/*******************************************************************************
 * FileIO.java: Java class to handle Input/Output. File used is "HighScores.txt"
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

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Player {
    String name;
    String score;
    String Date;

    public Player(String n, String s, String date)
    {
        name = n;
        score = s;
        Date = date;
    }

    public String getName()
    {
        return this.name;
    }

    public String getScore()
    {

        return this.score;
    }
    public String getDate()
    {
        return this.Date;
    }

    public static Comparator<Player> compareScores = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            int res = 0;
            SimpleDateFormat sdfo = new SimpleDateFormat("mm:ss");
            try{
                java.util.Date d1 = sdfo.parse(o1.getScore());
                java.util.Date d2 = sdfo.parse(o2.getScore());
                res = d1.compareTo(d2);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }
    };

    public static Comparator<Player> compareDates = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            int res = 0;
            SimpleDateFormat sdfo = new SimpleDateFormat("MM/dd/yyyy");
            try{
                java.util.Date d1 = sdfo.parse(o1.getDate());
                java.util.Date d2 = sdfo.parse(o2.getDate());
                res = d1.compareTo(d2);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }
    };
}
