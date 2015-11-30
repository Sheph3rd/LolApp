package com.pmul.league.lolapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Rodi on 30/11/2015.
 */
public class BD_LOLUniversity extends SQLiteOpenHelper
{
    static final String DATABASE_NAME = "BDLOLUniversity.db";
    static final int DATABASE_VERSION = 1;

    // TABLA CAMPEONES
    static final String TABLE_CHAMPIONS = "champions";
    static final String COLUMN_ID = "id";
    static final String COLUMN_LORE = "lore";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_TIPS = "tips";
    static final String COLUMN_IMG = "img";

    // TABLA HABILIDADES
    static final String TABLE_SKILLS = "skills";
    static final String COLUMN_DESCRIPTION = "description";
    static final String COLUMN_DETAIL = "detail";
    static final String COLUMN_SKILL_NAME = "skill_name";
    static final String COLUMN_SKILL_VIDEO = "skill_video";
    static final String COLUMN_CHAR = "cast_char";
    static final String COLUMN_SKILL_IMG = "skill_img";

    //TABLA SKIN
    static final String TABLE_SKINS = "skins";
    static final String COLUMN_SKIN_NAME = "skin_name";
    static final String COLUMN_SKIN_VIDEO = "skin_video";
    static final String COLUMN_SKIN_IMG = "skin_img";

    static final String CREATE_CHAMPION_TABLE =
            "CREATE TABLE " + TABLE_CHAMPIONS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_LORE + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_TIPS + " TEXT,"
                    + COLUMN_IMG + " BLOB"
                    + ");";

//boaaa vin disel
    //boaaa mac nou

    static final String CREATE_SKILLS_TABLE =
            "CREATE TABLE " + TABLE_SKILLS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_DETAIL + " TEXT,"
                    + COLUMN_CHAR + " TEXT,"
                    + COLUMN_SKILL_NAME + " TEXT,"
                    + COLUMN_SKILL_VIDEO + " TEXT,"
                    + COLUMN_SKILL_IMG + " BLOB"
                    + ");";

    static final String CREATE_SKINS_TABLE =
            "CREATE TABLE " + TABLE_SKINS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_SKIN_NAME + " TEXT,"
                    + COLUMN_SKIN_VIDEO + " TEXT,"
                    + COLUMN_SKIN_IMG + " BLOB"
                    + ");";

    static final String DROP_TABLE_CHAMPIONS = "DROP TABLE IF EXISTS " + TABLE_CHAMPIONS;
    static final String DROP_TABLE_SKINS = "DROP TABLE IF EXISTS " + TABLE_SKINS;
    static final String DROP_TABLE_SKILLS = "DROP TABLE IF EXISTS " + TABLE_SKILLS;

    public BD_LOLUniversity(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_CHAMPION_TABLE);
        db.execSQL(CREATE_SKILLS_TABLE);
        db.execSQL(CREATE_SKINS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE_CHAMPIONS);
        db.execSQL(DROP_TABLE_SKILLS);
        db.execSQL(DROP_TABLE_SKINS);

        db.execSQL(CREATE_CHAMPION_TABLE);
        db.execSQL(CREATE_SKILLS_TABLE);
        db.execSQL(CREATE_SKINS_TABLE);
    }

    /*
    @Override
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                note.setText(cursor.getString(
                        cursor.getColumnIndex(COLUMN_TEXT)));
                notes.add(note);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return notes;
    }

    @Override
    public void addNote(Note note) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_TEXT, note.getText());

        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    @Override
    public void deleteNote(int noteId) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(TABLE_NOTES, COLUMN_ID + " = ?",
                new String[] {String.valueOf(noteId)});
        db.close();
    }

    @Override
    public Note getNote(int noteId) {
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " +
                COLUMN_ID + " = " + noteId;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Note note = new Note();
        if(cursor.moveToFirst()) {
            note.setId(cursor.getInt(
                    cursor.getColumnIndex(COLUMN_ID)));
            note.setTitle(cursor.getString(
                    cursor.getColumnIndex(COLUMN_TITLE)));
            note.setText(cursor.getString(
                    cursor.getColumnIndex(COLUMN_TEXT)));
        }
        cursor.close();
        db.close();

        return note;
    }
*/
}
