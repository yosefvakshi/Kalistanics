package com.example.kalistanics;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        
        // יצירת אתגר לדוגמה אם אין אתגרים במסד הנתונים
        if (dbHelper.getAllChallenges().getCount() == 0) {
            createSampleChallenge();
        }

        Button myButton = findViewById(R.id.button1);
        Button myButton2 = findViewById(R.id.myButton2);

        if (myButton == null || myButton2 == null) {
            Toast.makeText(this, "שגיאה: כפתור לא נמצא ב-XML", Toast.LENGTH_SHORT).show();
            return;
        }

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // שליחת ה-ID של האתגר הראשון (לדוגמה)
                Intent intent = new Intent(MainActivity.this, LevelMuscleUpp.class);
                intent.putExtra("challenge_id", 1L); // שימוש ב-ID של האתגר הראשון
                startActivity(intent);
            }
        });

        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "כפתור 2 נלחץ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createSampleChallenge() {
        // יצירת אתגר לדוגמה
        long challengeId = dbHelper.addChallenge("Muscle Up Challenge");
        
        // יצירת 5 רמות
        String[] levelNames = {
            "Level 1: Pull Up",
            "Level 2: High Pull Up",
            "Level 3: Jumping Muscle Up",
            "Level 4: Kipping Muscle Up",
            "Level 5: Muscle Up"
        };

        for (int i = 0; i < levelNames.length; i++) {
            long levelId = dbHelper.addLevel(challengeId, levelNames[i]);
            
            // הוספת 4 תרגילים לכל רמה
            for (int j = 1; j <= 4; j++) {
                dbHelper.addTraining(levelId, R.drawable.muscle_upp, "הסבר " + j + " ל" + levelNames[i]);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
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
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
