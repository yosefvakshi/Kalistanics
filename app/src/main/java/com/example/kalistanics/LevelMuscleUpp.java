package com.example.kalistanics;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LevelMuscleUpp extends AppCompatActivity {
    private HorizontalScrollView scrollView1, scrollView2, scrollView3, scrollView4, scrollView5;
    private Button button1, button2, button3, button4, button5;
    private Button completeButton1, completeButton2, completeButton3, completeButton4, completeButton5;
    private boolean isLevel1Open = false;
    private boolean isLevel2Open = false;
    private boolean isLevel3Open = false;
    private boolean isLevel4Open = false;
    private boolean isLevel5Open = false;
    private boolean[] isCompleted = new boolean[5];
    private DatabaseHelper dbHelper;
    private long currentChallengeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_muscle_upp);

        dbHelper = new DatabaseHelper(this);

        // קבלת מזהה האתגר מה-Intent
        currentChallengeId = getIntent().getLongExtra("challenge_id", -1);
        if (currentChallengeId == -1) {
            Toast.makeText(this, "שגיאה בטעינת האתגר", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // אתחול כפתורים
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        // אתחול כפתורי הצלחה
        completeButton1 = findViewById(R.id.completeButton1);
        completeButton2 = findViewById(R.id.completeButton2);
        completeButton3 = findViewById(R.id.completeButton3);
        completeButton4 = findViewById(R.id.completeButton4);
        completeButton5 = findViewById(R.id.completeButton5);

        // אתחול אזורי גלילה
        scrollView1 = findViewById(R.id.scrollView1);
        scrollView2 = findViewById(R.id.scrollView2);
        scrollView3 = findViewById(R.id.scrollView3);
        scrollView4 = findViewById(R.id.scrollView4);
        scrollView5 = findViewById(R.id.scrollView5);

        // טעינת נתונים מהמסד
        loadLevelData();

        // הגדרת מאזיני לחיצה לכפתורים
        button1.setOnClickListener(v -> toggleLevel(1));
        button2.setOnClickListener(v -> toggleLevel(2));
        button3.setOnClickListener(v -> toggleLevel(3));
        button4.setOnClickListener(v -> toggleLevel(4));
        button5.setOnClickListener(v -> toggleLevel(5));

        // הגדרת מאזיני לחיצה לכפתורי הצלחה
        completeButton1.setOnClickListener(v -> handleLevelCompletion(1));
        completeButton2.setOnClickListener(v -> handleLevelCompletion(2));
        completeButton3.setOnClickListener(v -> handleLevelCompletion(3));
        completeButton4.setOnClickListener(v -> handleLevelCompletion(4));
        completeButton5.setOnClickListener(v -> handleLevelCompletion(5));
    }

    private void loadLevelData() {
        // טעינת נתונים לכל שלב
        loadTrainingsForLevel(1, scrollView1);
        loadTrainingsForLevel(2, scrollView2);
        loadTrainingsForLevel(3, scrollView3);
        loadTrainingsForLevel(4, scrollView4);
        loadTrainingsForLevel(5, scrollView5);
    }

    private void loadTrainingsForLevel(int levelNumber, HorizontalScrollView scrollView) {
        // יצירת מיכל לתרגילים
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.HORIZONTAL);
        scrollView.addView(container);

        // קבלת התרגילים מהמסד
        Cursor cursor = dbHelper.getTrainingsForLevel(levelNumber);
        while (cursor.moveToNext()) {
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRAINING_DESCRIPTION));
            String imageName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRAINING_IMAGE));

            // יצירת תצוגת תרגיל
            View exerciseView = getLayoutInflater().inflate(R.layout.exercise_cards, container, false);
            
            ImageView imageView = exerciseView.findViewById(R.id.image1);
            TextView textView = exerciseView.findViewById(R.id.text1);

            // הגדרת תמונה וטקסט
            int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
            imageView.setImageResource(imageResource);
            textView.setText(description);

            container.addView(exerciseView);
        }
        cursor.close();
    }

    private void toggleLevel(int level) {
        switch (level) {
            case 1:
                isLevel1Open = !isLevel1Open;
                scrollView1.setVisibility(isLevel1Open ? View.VISIBLE : View.GONE);
                break;
            case 2:
                isLevel2Open = !isLevel2Open;
                scrollView2.setVisibility(isLevel2Open ? View.VISIBLE : View.GONE);
                break;
            case 3:
                isLevel3Open = !isLevel3Open;
                scrollView3.setVisibility(isLevel3Open ? View.VISIBLE : View.GONE);
                break;
            case 4:
                isLevel4Open = !isLevel4Open;
                scrollView4.setVisibility(isLevel4Open ? View.VISIBLE : View.GONE);
                break;
            case 5:
                isLevel5Open = !isLevel5Open;
                scrollView5.setVisibility(isLevel5Open ? View.VISIBLE : View.GONE);
                break;
        }
    }

    private void handleLevelCompletion(int level) {
        Button completeButton;
        String message;
        int levelIndex = level - 1;
        
        switch (level) {
            case 1:
                completeButton = completeButton1;
                message = "מעולה! הצלחת! עכשיו תעבור לשלב השני";
                break;
            case 2:
                completeButton = completeButton2;
                message = "מעולה! הצלחת! עכשיו תעבור לשלב השלישי";
                break;
            case 3:
                completeButton = completeButton3;
                message = "מעולה! הצלחת! עכשיו תעבור לשלב הרביעי";
                break;
            case 4:
                completeButton = completeButton4;
                message = "מעולה! הצלחת! עכשיו תעבור לשלב החמישי";
                break;
            case 5:
                completeButton = completeButton5;
                message = "מעולה! הצלחת! סיימת את כל השלבים!";
                break;
            default:
                return;
        }

        // החלפת מצב ההשלמה
        isCompleted[levelIndex] = !isCompleted[levelIndex];

        // שינוי הטקסט בהתאם למצב
        if (isCompleted[levelIndex]) {
            completeButton.setText("✓");
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            completeButton.setText("הצלחתי");
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}

    
