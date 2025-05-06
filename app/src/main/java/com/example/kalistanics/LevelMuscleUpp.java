package com.example.kalistanics;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LevelMuscleUpp extends AppCompatActivity {
    private ScrollView scrollView;
    private SeekBar seekBar;
    private DatabaseHelper dbHelper;
    private long challengeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_muscle_upp);

        dbHelper = new DatabaseHelper(this);
        challengeId = getIntent().getLongExtra("challenge_id", -1);

        if (challengeId == -1) {
            Toast.makeText(this, "שגיאה: לא נמצא מזהה אתגר", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        scrollView = findViewById(R.id.main);
        seekBar = findViewById(R.id.scrollBar);

        loadLevelsFromDatabase();
    }

    private void loadLevelsFromDatabase() {
        Cursor cursor = dbHelper.getLevelsForChallenge(challengeId);
        
        while (cursor.moveToNext()) {
            long levelId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            String levelName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int levelNumber = cursor.getInt(cursor.getColumnIndexOrThrow("level_number"));
            boolean isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("is_completed")) == 1;

            // מציאת הכפתור המתאים לפי מספר הרמה
            String buttonId = "button" + levelNumber;
            int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
            Button button = findViewById(resId);

            if (button != null) {
                button.setText(levelName);
                setupButton(button, levelId, levelNumber, isCompleted);
            }
        }
        cursor.close();
    }

    private void setupButton(Button button, long levelId, int levelNumber, boolean isCompleted) {
        // מציאת ה-layout המתאים לפי מספר הרמה
        String layoutId = "layout" + levelNumber;
        int resId = getResources().getIdentifier(layoutId, "id", getPackageName());
        LinearLayout layout = findViewById(resId);

        if (layout != null) {
            button.setOnClickListener(v -> {
                if (layout.getVisibility() == View.VISIBLE) {
                    layout.setVisibility(View.GONE);
                } else {
                    layout.setVisibility(View.VISIBLE);
                    loadTrainings(levelId, layout);
                }
            });

            // מציאת כפתור ההשלמה
            String completeButtonId = "completeButton" + levelNumber;
            int completeButtonResId = getResources().getIdentifier(completeButtonId, "id", getPackageName());
            Button completeButton = findViewById(completeButtonResId);

            if (completeButton != null) {
                completeButton.setOnClickListener(v -> {
                    dbHelper.updateLevelCompletion(levelId, true);
                    Toast.makeText(this, "כל הכבוד! השלמת את הרמה", Toast.LENGTH_SHORT).show();
                    completeButton.setEnabled(false);
                });
            }
        }
    }

    private void loadTrainings(long levelId, LinearLayout layout) {
        Cursor cursor = dbHelper.getTrainingsForLevel(levelId);
        
        while (cursor.moveToNext()) {
            int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("image"));
            String explanation = cursor.getString(cursor.getColumnIndexOrThrow("explanation"));

            // מציאת ה-ImageView וה-TextView המתאימים
            String imageId = "image" + (cursor.getPosition() + 1);
            String textId = "text" + (cursor.getPosition() + 1);
            
            int imageResId2 = getResources().getIdentifier(imageId, "id", getPackageName());
            int textResId = getResources().getIdentifier(textId, "id", getPackageName());
            
            ImageView imageView = layout.findViewById(imageResId2);
            TextView textView = layout.findViewById(textResId);

            if (imageView != null && textView != null) {
                imageView.setImageResource(imageResId);
                textView.setText(explanation);
            }
        }
        cursor.close();
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            finishAffinity(); // סוגר את כל האקטיביטיז ויוצא מהאפליקציה
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

    
