package com.example.kalistanics;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // הגדרת כפתורים
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        // הגדרת מאזיני לחיצה
        button1.setOnClickListener(v -> openChallenge(1));
        button2.setOnClickListener(v -> openChallenge(2));
        button3.setOnClickListener(v -> openChallenge(3));
        button4.setOnClickListener(v -> openChallenge(4));
    }

    private void openChallenge(int challengeNumber) {
        // קבלת מזהה האתגר מהמסד
        Cursor cursor = dbHelper.getAllChallenges();
        if (cursor.moveToPosition(challengeNumber - 1)) {
            long challengeId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHALLENGE_ID));
            cursor.close();

            // פתיחת דף השלבים עם מזהה האתגר
            Intent intent = new Intent(this, LevelMuscleUpp.class);
            intent.putExtra("challenge_id", challengeId);
            startActivity(intent);
        } else {
            Toast.makeText(this, "שגיאה בטעינת האתגר", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
