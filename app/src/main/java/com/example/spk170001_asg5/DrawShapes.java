/*******************************************************************************
 * DrawShapes.java: Java class which draws the shapes on the canvas.
 *
 * This class primarily generates non overlapping shapes and draws them on the
 * canvas. Each shape has a random lifetime, after which the shape disappears
 * from the canvas.
 *
 * Lifetimes of shapes are checked at every tick of a CountDownTimer.
 *
 * This class also receives the touch events by the user and the shape which needs
 * to disappear from the canvas. When a shape is clicked on, another random shape
 * appears at a random position.
 *
 * We keep track of the number of shapes that the user should have touched so that
 * penalty can be added to the final score, if any.
 *
 * Written by Sanket Kulkarni (spk170001) at The University of Texas at Dallas
 * starting March 20, 2020.
 ******************************************************************************/
package com.example.spk170001_asg5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.CountDownTimer;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

public class DrawShapes extends View {
    public ArrayList<ShapeDrawable> shapes = new ArrayList<>();     //store all the shapes to be drawn on the canvas
    public ArrayList<ShapeDrawable> rects = new ArrayList<>();      //store the squares. used to detect correct press
    public ShapeDrawable mDrawable = new ShapeDrawable();
    Integer[] colors = {Color.RED, Color.GREEN, Color.YELLOW, Color.WHITE, Color.MAGENTA, Color.BLUE};
    public ShapeDrawable removable = new ShapeDrawable();           // shape to be removed from the canvas
    public ArrayList<Integer> lifetimes = new ArrayList<>();        // check at each tick of timer if lifetime has expired
    public ArrayList<Integer> all_lifetimes = new ArrayList<>();    // used to add penalty to the score of the user.
    int indicator = 1;
    int redshapes = 0;
    int missed_time = 0;

    public DrawShapes(Context ApplicationContext)
    {
        super(ApplicationContext);
        //Initially add 5 shapes of each, circles and rectangles respectively to the canvas.
        addshapes(5);
        invalidate();

        // Timer to check at each tick whether the lifetime of any shape has reached to 0. If so
        // remove the shape from the canvas.
        new CountDownTimer(100000000, 1000)
        {
            public void onTick(long mill)
            {
                for(int i = 0; i < shapes.size(); i++)
                {
                    int current = lifetimes.get(i);
                    if(current < 0)
                    {
                        ShapeDrawable drawable = shapes.get(i);
                        // Check if the user has missed a RED square. If yes, increment the time so that
                        // it can be added to the final score.
                        if(rects.contains(drawable) && drawable.getPaint().getColor() == Color.RED)
                        {
                            missed_time += all_lifetimes.get(i);
                        }
                        lifetimes.remove(i);
                        shapes.remove(i);
                        all_lifetimes.remove(i);
                        removable = new ShapeDrawable();
                    }
                    else
                    {
                        current -= 1;
                        lifetimes.set(i, current);
                    }
                }

                // Keep the number of shapes on the canvas greater than 6.
                if(shapes.size() > 0 && shapes.size() < 6)
                {
                    addshapes(3);
                }
                invalidate();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    // Public method to add shapes to the canvas. This method adds "num" number of squares and
    // circles respectively.
    public void addshapes(int num)
    {
        for(int i = 0; i < num; i++)
        {
            // Generate a new square
            mDrawable = new ShapeDrawable(new RectShape());
            Random random = new Random();
            int color = random.nextInt(6);
            mDrawable.getPaint().setColor(colors[color]);
            int x = random.nextInt(900) + 60;
            int y = random.nextInt(1200) + 100;
            float time = (float)random.nextInt(3);
            int width = random.nextInt(450) + 100;
            int height = width;
            int flag = 0;
            int lifetime = random.nextInt(3) + 2;
            mDrawable.setBounds(x, y, x + width, y + height);
            for(ShapeDrawable shape : shapes)
            {
                // If generated shape overlaps/intersects with shape on canvas, discard it and draw
                // new one.
                if(shape.getBounds().intersects(x, y, x + width, y + width))
                {
                    flag = 1;
                    break;
                }
            }
            // Shape does not intersect. We can draw
            if(flag == 0)
            {
                shapes.add(mDrawable);
                lifetimes.add(lifetime);
                rects.add(mDrawable);
                if(color == 0)
                {
                    redshapes++;
                }
                all_lifetimes.add(lifetime);
            }
            // Shape intersects with shape on canvas. Discard and generate new one.
            else
            {
                i-=1;
            }
        }

        for(int i = 0; i < num; i++)
        {
            // Generate a new circle
            mDrawable = new ShapeDrawable(new OvalShape());
            Random random = new Random();
            int color = random.nextInt(6);
            mDrawable.getPaint().setColor(colors[color]);
            int x = random.nextInt(900) + 60;
            int y = random.nextInt(800) + 100;
            float time = (float)random.nextInt(3);
            int width = random.nextInt(450) + 100;
            int height = width;
            int flag = 0;
            int lifetime = random.nextInt(3) + 2;
            mDrawable.setBounds(x, y, x + width, y + height);
            for(ShapeDrawable shape : shapes)
            {
                // If generated shape overlaps/intersects with shape on canvas, discard it and draw
                // new one.
                if(shape.getBounds().intersects(x, y, x + width, y + width))
                {
                    flag = 1;
                    break;
                }
            }
            // Shape does not intersect. We can draw
            if(flag == 0)
            {
                shapes.add(mDrawable);
                lifetimes.add(lifetime);
                all_lifetimes.add(lifetime);
            }
            // Shape intersects with some shape on canvas. Discard and generate new one.
            else
            {
                i-=1;
            }
        }
    }

    // Invoked on invalidate(). This method draws the shapes on the canvas from the
    // Arraylist<ShapeDrawable> shapes. If a shape has been touched by the user, remove
    // and generate a new shape at random position on the canvas.
    public void onDraw(Canvas canvas)
    {
        if(indicator == 0)
        {
            for(int i = 0; i < shapes.size(); i++)
            {
                ShapeDrawable shape = shapes.get(i);
                if(shape.equals(removable))
                {
                    shapes.remove(i);
                    all_lifetimes.remove(i);
                    lifetimes.remove(i);
                    if(rects.contains(shape))
                    {
                        rects.remove(shape);
                    }
                }
            }
            indicator = 1;

            //Add a shape on the canvas
            for(int i = 0; i < 1; i++)
            {
                Random random = new Random();
                int type = random.nextInt(2) + 1;
                if(type == 1)
                {
                    mDrawable = new ShapeDrawable(new RectShape());
                }
                else
                {
                    mDrawable = new ShapeDrawable(new OvalShape());
                }
                int color = random.nextInt(6);
                mDrawable.getPaint().setColor(colors[color]);
                int x = random.nextInt(900) + 60;
                int y = random.nextInt(800) + 100;
                float time = (float)random.nextInt(3);
                int width = random.nextInt(450) + 100;
                int height = width;
                int flag = 0;
                int lifetime = random.nextInt(3) + 2;
                mDrawable.setBounds(x, y, x + width, y + height);
                for(ShapeDrawable shape : shapes)
                {
                    if(shape.getBounds().intersects(x, y, x + width, y + width))
                    {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0)
                {
                    if(type == 1)       //Shape is a square
                    {
                        rects.add(mDrawable);
                        if(color == 0)
                        {
                            redshapes++;    // User has to pop the square
                        }
                    }
                    shapes.add(mDrawable);
                    lifetimes.add(lifetime);
                    all_lifetimes.add(lifetime);
                }
                else
                {
                    i-=1;
                }
            }
        }

        //Draw Shapes on canvas
        for(ShapeDrawable shape : shapes)
        {
            shape.draw(canvas);
        }
    }
}
