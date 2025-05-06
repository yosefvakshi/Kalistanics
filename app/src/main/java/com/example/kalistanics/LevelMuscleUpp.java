package com.example.kalistanics;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LevelMuscleUpp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_muscle_upp);

        // הגדרת כפתורים
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);

        // הגדרת תצוגות אופקיות
        HorizontalScrollView scrollView1 = findViewById(R.id.scrollView1);
        HorizontalScrollView scrollView2 = findViewById(R.id.scrollView2);
        HorizontalScrollView scrollView3 = findViewById(R.id.scrollView3);
        HorizontalScrollView scrollView4 = findViewById(R.id.scrollView4);
        HorizontalScrollView scrollView5 = findViewById(R.id.scrollView5);

        // הגדרת כפתורי השלמה
        Button completeButton1 = findViewById(R.id.completeButton1);
        Button completeButton2 = findViewById(R.id.completeButton2);
        Button completeButton3 = findViewById(R.id.completeButton3);
        Button completeButton4 = findViewById(R.id.completeButton4);
        Button completeButton5 = findViewById(R.id.completeButton5);

        // הגדרת תמונות
        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);
        ImageView image4 = findViewById(R.id.image4);

        // הגדרת טקסטים
        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);
        TextView text3 = findViewById(R.id.text3);
        TextView text4 = findViewById(R.id.text4);

        // הגדרת תמונות
        image1.setImageResource(R.drawable.muscle_upp);
        image2.setImageResource(R.drawable.muscle_upp);
        image3.setImageResource(R.drawable.muscle_upp);
        image4.setImageResource(R.drawable.muscle_upp);

        // הגדרת טקסטים
        text1.setText("הסבר 1");
        text2.setText("הסבר 2");
        text3.setText("הסבר 3");
        text4.setText("הסבר 4");

        // הגדרת כפתורים
        setupButton(button1, scrollView1, completeButton1);
        setupButton(button2, scrollView2, completeButton2);
        setupButton(button3, scrollView3, completeButton3);
        setupButton(button4, scrollView4, completeButton4);
        setupButton(button5, scrollView5, completeButton5);
    }

    private void setupButton(Button button, HorizontalScrollView scrollView, Button completeButton) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // הסתרת כל התצוגות האופקיות
                findViewById(R.id.scrollView1).setVisibility(View.GONE);
                findViewById(R.id.scrollView2).setVisibility(View.GONE);
                findViewById(R.id.scrollView3).setVisibility(View.GONE);
                findViewById(R.id.scrollView4).setVisibility(View.GONE);
                findViewById(R.id.scrollView5).setVisibility(View.GONE);

                // הצגת התצוגה האופקית הנבחרת
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LevelMuscleUpp.this, "כל הכבוד! השלמת את השלב", Toast.LENGTH_SHORT).show();
                completeButton.setEnabled(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            finishAffinity();
            return true;
        } else if (item.getItemId() == R.id.action_main) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

    
