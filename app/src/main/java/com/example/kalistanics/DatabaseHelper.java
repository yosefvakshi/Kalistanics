package com.example.kalistanics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "KalistanicsDB";
    private static final int DATABASE_VERSION = 1;

    // טבלת אתגרים (Challenges)
    public static final String TABLE_CHALLENGES = "challenges";
    public static final String COLUMN_CHALLENGE_ID = "id";
    public static final String COLUMN_CHALLENGE_TITLE = "title";

    // טבלת רמות (Levels)
    public static final String TABLE_LEVELS = "levels";
    public static final String COLUMN_LEVEL_ID = "id";
    public static final String COLUMN_LEVEL_CHALLENGE_ID = "challenge_id";
    public static final String COLUMN_LEVEL_NAME = "name";
    public static final String COLUMN_LEVEL_COMPLETED = "completed";

    // טבלת תרגילים (Trainings)
    public static final String TABLE_TRAININGS = "trainings";
    public static final String COLUMN_TRAINING_ID = "id";
    public static final String COLUMN_TRAINING_LEVEL_ID = "level_id";
    public static final String COLUMN_TRAINING_IMAGE = "image";
    public static final String COLUMN_TRAINING_EXPLANATION = "explanation";

    // יצירת טבלת אתגרים
    private static final String CREATE_TABLE_CHALLENGES = 
        "CREATE TABLE " + TABLE_CHALLENGES + "(" +
        COLUMN_CHALLENGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_CHALLENGE_TITLE + " TEXT" +
        ")";

    // יצירת טבלת רמות
    private static final String CREATE_TABLE_LEVELS = 
        "CREATE TABLE " + TABLE_LEVELS + "(" +
        COLUMN_LEVEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_LEVEL_CHALLENGE_ID + " INTEGER, " +
        COLUMN_LEVEL_NAME + " TEXT, " +
        COLUMN_LEVEL_COMPLETED + " INTEGER DEFAULT 0, " +
        "FOREIGN KEY(" + COLUMN_LEVEL_CHALLENGE_ID + ") REFERENCES " + 
        TABLE_CHALLENGES + "(" + COLUMN_CHALLENGE_ID + ")" +
        ")";

    // יצירת טבלת תרגילים
    private static final String CREATE_TABLE_TRAININGS = 
        "CREATE TABLE " + TABLE_TRAININGS + "(" +
        COLUMN_TRAINING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_TRAINING_LEVEL_ID + " INTEGER, " +
        COLUMN_TRAINING_IMAGE + " INTEGER, " +
        COLUMN_TRAINING_EXPLANATION + " TEXT, " +
        "FOREIGN KEY(" + COLUMN_TRAINING_LEVEL_ID + ") REFERENCES " + 
        TABLE_LEVELS + "(" + COLUMN_LEVEL_ID + ")" +
        ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHALLENGES);
        db.execSQL(CREATE_TABLE_LEVELS);
        db.execSQL(CREATE_TABLE_TRAININGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAININGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHALLENGES);
        onCreate(db);
    }

    // הוספת אתגר חדש
    public long addChallenge(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHALLENGE_TITLE, title);
        return db.insert(TABLE_CHALLENGES, null, values);
    }

    // הוספת רמה חדשה
    public long addLevel(long challengeId, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LEVEL_CHALLENGE_ID, challengeId);
        values.put(COLUMN_LEVEL_NAME, name);
        return db.insert(TABLE_LEVELS, null, values);
    }

    // הוספת תרגיל חדש
    public long addTraining(long levelId, int image, String explanation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRAINING_LEVEL_ID, levelId);
        values.put(COLUMN_TRAINING_IMAGE, image);
        values.put(COLUMN_TRAINING_EXPLANATION, explanation);
        return db.insert(TABLE_TRAININGS, null, values);
    }

    // קבלת כל האתגרים
    public Cursor getAllChallenges() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CHALLENGES, null, null, null, null, null, null);
    }

    // קבלת כל הרמות של אתגר מסוים
    public Cursor getLevelsForChallenge(long challengeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_LEVELS, null, 
            COLUMN_LEVEL_CHALLENGE_ID + "=?", 
            new String[]{String.valueOf(challengeId)}, 
            null, null, null);
    }

    // קבלת כל התרגילים של רמה מסוימת
    public Cursor getTrainingsForLevel(long levelId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TRAININGS, null, 
            COLUMN_TRAINING_LEVEL_ID + "=?", 
            new String[]{String.valueOf(levelId)}, 
            null, null, null);
    }

    // עדכון סטטוס השלמת רמה
    public int updateLevelCompletion(long levelId, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LEVEL_COMPLETED, completed ? 1 : 0);
        return db.update(TABLE_LEVELS, values, 
            COLUMN_LEVEL_ID + "=?", 
            new String[]{String.valueOf(levelId)});
    }
} 