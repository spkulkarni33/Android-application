/*******************************************************************************
 * GameCanvas.java: Java class to handle the drawing of shapes on the canvas.
 *
 * This class detects the touch events by the user and identifies which shape
 * has been touched and needs to disappear.
 *
 * The score of the user is calculated and the current date, time and score is
 * sent to the EnterRecord activity to enter the score of the user.
 *
 * Information like final score and the number of shapes missed is displayed to
 * the user.
 *
 * Written by Sanket Kulkarni (spk170001) at The University of Texas at Dallas
 * starting March 20, 2020.
 ******************************************************************************/

package com.example.spk170001_asg5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameCanvas extends AppCompatActivity {
    int correct_touches = 0;
    int total_touches = 0;
    long final_score;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_canvas_screen);
        final LinearLayout canvas_layout = findViewById(R.id.canvas_layout);
        final DrawShapes dshapes = new DrawShapes(GameCanvas.this);
        canvas_layout.addView(dshapes);
        final long start_time = System.currentTimeMillis();

        //Detect touch event on the canvas
        canvas_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                TextView touch = findViewById(R.id.touches);
                switch(action)
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        //Co-ordinates at the ACTION_DOWN
                        int x = (int)event.getX();
                        int y = (int)event.getY();
                        total_touches += 1;
                        //loop to check if a shape has been touched
                        for(int i = 0; i < dshapes.shapes.size(); i++)
                        {
                            ShapeDrawable shape = dshapes.shapes.get(i);
                            if(shape.getBounds().contains(x, y))
                            {
                                //block to check if the shape that has been touched is a RED square
                                if(shape.getPaint().getColor() == Color.RED && dshapes.rects.contains(shape))
                                {
                                        correct_touches += 1;
                                }
                                touch.setText("Squares Touched: "+correct_touches);
                                dshapes.removable = shape;  //set the shape to be removed to the shape that has been touched.
                                dshapes.indicator = 0;      //indicate to DrawShapes that a shape has been touched
                                dshapes.invalidate();
                            }
                        }
                        // User has popped 10 RED squares. End the game and display statistics.
                        if(correct_touches == 10)
                        {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            final String[] datetime = dateFormat.format(date).split(" ");
                            long endtime = System.currentTimeMillis();
                            final_score = endtime - start_time;
                            dateFormat = new SimpleDateFormat("mm:ss");
                            final String score = dateFormat.format(final_score);
                            int miss = dshapes.redshapes - 10;         //Number of shapes that the user should have popped
                            // Form alert dialog
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameCanvas.this);
                            alertDialogBuilder.setMessage("Game Over!\nMissed Shapes: "+miss+"\n" +
                                    "Total Score: "+ score + "\nDo you wish to continue?");

                            //Collect information and send intent to enter score to the scoreboard.
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Intent intent = new Intent(getApplicationContext(), EnterRecord.class);
                                            intent.putExtra("Score", ""+score);
                                            intent.putExtra("Date", datetime[0]);
                                            intent.putExtra("Time", datetime[1]);
                                            startActivity(intent);
                                        }
                                    });

                            // Return to main screen of the application.
                            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        break;
                    }
                }
                return true;
            }
        });
    }
}
